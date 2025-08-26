import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TeleportModalDirective } from '../../../directives/teleport-modal.directive';
import { ItemResponseDTO } from '../../../interfaces/items-interface';
import { Subject, debounceTime } from 'rxjs';
import { ItemService } from '../../../service/service/item-service';

@Component({
  selector: 'app-item-list',
  standalone: true,
  imports: [TeleportModalDirective, FormsModule, CommonModule],
  templateUrl: './item-list.html',
  styleUrl: './item-list.scss'
})
export class ItemList implements OnInit, OnChanges {
  @Input() searchTerm: string = '';
  @Input() pageSizeInput: number = 5;
  @Output() itemSelected = new EventEmitter<ItemResponseDTO>();
  @Output() itemSaved = new EventEmitter<void>();

  @ViewChild('triggerButton', { static: false }) triggerButton!: ElementRef;

  items: ItemResponseDTO[] = [];
  selectedItem: ItemResponseDTO | null = null;
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
    private readonly itemService: ItemService,
    private readonly eRef: ElementRef
  ) {}

  ngOnInit(): void {
    this.loadItems();
    this.searchSubject.pipe(debounceTime(300)).subscribe(() => {
      this.currentPage = 0;
      this.loadItems();
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
      this.loadItems();
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
    this.loadItems();
  }

  trackById(index: number, item: ItemResponseDTO): number {
    return item.id;
  }

  loadItems(showLoading: boolean = true): void {
    this.loading = showLoading;
    const params = {
      page: this.currentPage,
      size: this.pageSize,
      sort: `${this.sortField},${this.sortDirection}`,
      search: this.searchQuery.trim(),
      searchField: this.searchField,
      unpaged: this.unpaged
    };

    this.itemService.getAllItems(params).subscribe({
      next: (data) => {
        if (data.status && data.data) {
          this.items = data.data.content ?? [];
          this.totalItems = data.data.page.totalElements;
          this.pageSize = data.data.page.size;
          this.currentPage = data.data.page.number;
          this.totalPages = data.data.page.totalPages;
          this.calculatePageNumbers();
        }
      },
      error: (error) => {
        console.error('Error fetching items', error);
        this.items = [];
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
      this.loadItems();
    }
  }

  editItem(item: ItemResponseDTO): void {
    console.log('Editing item:', item);
    this.selectedItem = item;
    this.selectedRowId = item.id;
    this.itemSelected.emit(item);
  }

  setItemToToggle(id: number): void {
    this.idToToggle = id;
  }

  toggleStatus(): void {
    if (!this.idToToggle) return;
    this.loading = true;
    this.itemService.toggleItemStatus(this.idToToggle).subscribe({
      next: () => {
        this.loadItems(false);
        this.closeModal('itemToggleConfirmation');
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

  onItemSaved(): void {
    this.loadItems(false);
    this.itemSaved.emit();
    this.resetForm();
  }

  resetForm(): void {
    this.selectedItem = null;
    this.selectedRowId = null;
  }
}
