import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TeleportModalDirective } from '../../../directives/teleport-modal.directive';
import { AddonsResponseDTO } from '../../../interfaces/addons-interface';
import { Subject, debounceTime } from 'rxjs';
import { AddonsService } from '../../../service/service/addons-service';

@Component({
  selector: 'app-addons-list',
  standalone: true,
  imports: [TeleportModalDirective, FormsModule, CommonModule],
  templateUrl: './addons-list.html',
  styleUrl: './addons-list.scss'
})
export class AddonsList implements OnInit, OnChanges {
  @Input() searchTerm: string = '';
  @Input() pageSizeInput: number = 5;
  @Output() addonsSelected = new EventEmitter<AddonsResponseDTO>();
  @Output() addonsSaved = new EventEmitter<void>();

  @ViewChild('triggerButton', { static: false }) triggerButton!: ElementRef;

  addons: AddonsResponseDTO[] = [];
  selectedAddons: AddonsResponseDTO | null = null;
  selectedRowId: number | null = null;
  idToToggle: number | null = null;

  loaderRows = Array(8).fill(0);
  loaderCells = Array(4).fill(0);

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
    private readonly addonsService: AddonsService,
    private readonly eRef: ElementRef
  ) {}

  ngOnInit(): void {
    this.loadAddons();
    this.searchSubject.pipe(debounceTime(300)).subscribe(() => {
      this.currentPage = 0;
      this.loadAddons();
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
      this.loadAddons();
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
    this.loadAddons();
  }

  trackById(index: number, addon: AddonsResponseDTO): number {
    return addon.id;
  }

  loadAddons(showLoading: boolean = true): void {
    this.loading = showLoading;
    const params = {
      page: this.currentPage,
      size: this.pageSize,
      sort: `${this.sortField},${this.sortDirection}`,
      search: this.searchQuery.trim(),
      searchField: this.searchField,
      unpaged: this.unpaged
    };

    this.addonsService.getAllAddons(params).subscribe({
      next: (data) => {
        if (data.status && data.data) {
          this.addons = data.data.content ?? [];
          this.totalItems = data.data.page.totalElements;
          this.pageSize = data.data.page.size;
          this.currentPage = data.data.page.number;
          this.totalPages = data.data.page.totalPages;
          this.calculatePageNumbers();
        }
      },
      error: (error) => {
        console.error('Error fetching add-ons', error);
        this.addons = [];
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
      this.loadAddons();
    }
  }

  editAddons(addon: AddonsResponseDTO): void {
    this.selectedAddons = addon;
    this.selectedRowId = addon.id;
    this.addonsSelected.emit(addon);
  }

  setAddonsToToggle(id: number): void {
    this.idToToggle = id;
  }

  toggleStatus(): void {
    if (!this.idToToggle) return;
    this.loading = true;
    this.addonsService.toggleAddonsStatus(this.idToToggle).subscribe({
      next: () => {
        this.loadAddons(false);
        this.closeModal('addonsToggleConfirmation');
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

  onAddonsSaved(): void {
    this.loadAddons(false);
    this.addonsSaved.emit();
    this.resetForm();
  }

  resetForm(): void {
    this.selectedAddons = null;
    this.selectedRowId = null;
  }
}
