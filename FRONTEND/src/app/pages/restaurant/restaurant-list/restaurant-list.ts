import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TeleportModalDirective } from '../../../directives/teleport-modal.directive';
import { RestaurantResponseDTO } from '../../../interfaces/restaurants-interface';
import { Subject, debounceTime } from 'rxjs';
import { RestaurantService } from '../../../service/service/restaurant-service';

@Component({
  selector: 'app-restaurant-list',
  standalone: true,
  imports: [TeleportModalDirective, FormsModule, CommonModule],
  templateUrl: './restaurant-list.html',
  styleUrl: './restaurant-list.scss'
})
export class RestaurantList implements OnInit, OnChanges {
  @Input() searchTerm: string = '';
  @Input() pageSizeInput: number = 5;
  @Output() restaurantSelected = new EventEmitter<RestaurantResponseDTO>();
  @Output() restaurantSaved = new EventEmitter<void>();

  @ViewChild('triggerButton', { static: false }) triggerButton!: ElementRef;

  restaurants: RestaurantResponseDTO[] = [];
  selectedRestaurant: RestaurantResponseDTO | null = null;
  selectedRowId: number | null = null;
  idToToggle: number | null = null;

  loaderRows = Array(8).fill(0);
  loaderCells = Array(6).fill(0);

  totalItems: number = 0;
  totalPages: number = 0;
  pageSize: number = 5;
  currentPage: number = 0;
  sortField: string = 'name';
  sortDirection: string = 'asc';
  searchQuery: string = '';
  searchField: string = 'name';
  allPageSize: number = 1000;
  unpaged: boolean = false;
  loading = false;

  public searchSubject = new Subject<void>();
  pageNumbers: number[] = [];

  constructor(
    private readonly restaurantService: RestaurantService,
    private readonly eRef: ElementRef
  ) {}

  ngOnInit(): void {
    this.loadRestaurants();
    this.searchSubject.pipe(debounceTime(300)).subscribe(() => {
      this.currentPage = 0;
      this.loadRestaurants();
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['searchTerm']) {
      this.searchQuery = this.searchTerm;
      this.searchSubject.next();
    }
    if (changes['pageSizeInput']) {
      this.unpaged = this.pageSizeInput === -1;
      this.pageSize = this.unpaged ? this.allPageSize : this.pageSizeInput;
      this.loadRestaurants();
    }
  }

  handleKeyDown(event: KeyboardEvent): void {
    if (event.key === 'Enter') {
      const field = event.target as HTMLElement;
      const fieldName = field?.textContent?.trim().toLowerCase();
      if (fieldName) this.changeSort(fieldName);
    }
  }

  changeSort(field: string): void {
    if (this.sortField === field) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = field;
      this.searchField = field;
      this.sortDirection = 'asc';
    }
    this.loadRestaurants();
  }

  trackById(index: number, restaurant: RestaurantResponseDTO): number {
    return restaurant.id;
  }

  loadRestaurants(showLoading: boolean = true): void {
    this.loading = showLoading;
    const params = {
      page: this.currentPage,
      size: this.pageSize,
      sort: `${this.sortField},${this.sortDirection}`,
      search: this.searchQuery.trim(),
      searchField: this.searchField,
      unpaged: this.unpaged
    };

    this.restaurantService.getAllRestaurants(params).subscribe({
      next: (data) => {
        if (data.status && data.data) {
          this.restaurants = data.data.content ?? [];
          this.totalItems = data.data.page.totalElements;
          this.pageSize = data.data.page.size;
          this.currentPage = data.data.page.number;
          this.totalPages = data.data.page.totalPages;
          this.calculatePageNumbers();
        }
      },
      error: (error) => {
        console.error('Error fetching restaurants', error);
        this.restaurants = [];
        this.totalItems = 0;
      },
      complete: () => {
        this.loading = false;
      },
    });
  }

  calculatePageNumbers(): void {
    this.pageNumbers = [];
    const totalPages = Math.ceil(this.totalItems / this.pageSize);
    for (let i = 0; i < totalPages; i++) this.pageNumbers.push(i);
  }

  getPageNumbers(): number[] {
    const start = Math.max(0, this.currentPage - 1);
    const end = Math.min(this.totalPages - 1, this.currentPage + 1);
    return Array.from({ length: end - start + 1 }, (_, i) => i + start);
  }

  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadRestaurants();
    }
  }

  editRestaurant(restaurant: RestaurantResponseDTO): void {
    this.selectedRestaurant = restaurant;
    this.selectedRowId = restaurant.id;
    this.restaurantSelected.emit(restaurant);
  }

  setRestaurantToToggle(id: number): void {
    this.idToToggle = id;
  }

  toggleStatus(): void {
    if (!this.idToToggle) return;
    this.loading = true;
    this.restaurantService.toggleRestaurantStatus(this.idToToggle).subscribe({
      next: () => {
        this.loadRestaurants(false);
        this.closeModal('restaurantToggleConfirmation');
      },
      error: (error) => {
        console.error('Error toggling status', error);
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  closeModal(modalId: string): void {
    const modal = document.getElementById(modalId);
    if (modal) {
      const modalInstance = (window as any).bootstrap.Modal.getInstance(modal);
      if (modalInstance) modalInstance.hide();
    }
    this.idToToggle = null;
    this.triggerButton?.nativeElement?.focus();
  }

  onRestaurantSaved(): void {
    this.loadRestaurants(false);
    this.restaurantSaved.emit();
    this.resetForm();
  }

  resetForm(): void {
    this.selectedRestaurant = null;
    this.selectedRowId = null;
  }
}
