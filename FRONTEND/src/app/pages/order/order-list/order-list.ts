import { CommonModule } from '@angular/common';
import {
  Component,
  ElementRef,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Subject, debounceTime } from 'rxjs';
import { TeleportModalDirective } from '../../../directives/teleport-modal.directive';
import { OrderDTO } from '../../../interfaces/orders-interface';
import { OrderService } from './../../../service/service/order-service';

@Component({
  selector: 'app-order-list',
  imports: [TeleportModalDirective, FormsModule, CommonModule],
  templateUrl: './order-list.html',
  styleUrl: './order-list.scss',
})
export class OrderList implements OnInit, OnChanges {
  @Input() searchTerm: string = '';
  @Input() pageSizeInput: number = 5;
  idToToggle: number | null = null;

  @ViewChild('triggerButton', { static: false }) triggerButton!: ElementRef;

  orders: OrderDTO[] = [];

  loaderRows = Array(8).fill(0);
  loaderCells = Array(4).fill(0);

  totalItems: number = 0;
  totalPages: number = 0;
  pageSize: number = 5;
  currentPage: number = 0;
  sortField: string = 'orderDate';
  sortDirection: string = 'asc';
  searchQuery: string = '';
  searchField: string = 'name';
  allPageSize: number = 1000;
  unpaged: boolean = false;
  loading = false;
  public searchSubject = new Subject<void>();
  pageNumbers: number[] = [];

  constructor(
    private readonly orderService: OrderService,
    private readonly eRef: ElementRef
  ) {}

  ngOnInit(): void {
    this.loadOrders();
    this.searchSubject.pipe(debounceTime(300)).subscribe(() => {
      this.currentPage = 0;
      this.loadOrders();
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
      this.loadOrders();
    }
  }

  loadOrders(showLoading: boolean = true): void {
    this.loading = showLoading;
    const params = {
      page: this.currentPage,
      size: this.pageSize,
      sort: `${this.sortField},${this.sortDirection}`,
      search: this.searchQuery.trim(),
      searchField: this.searchField,
      unpaged: this.unpaged,
    };

    this.orderService.getAllOrder(params).subscribe({
      next: (data) => {
        if (data.status && data.data) {
          this.orders = data.data.content ?? [];
          this.totalItems = data.data.page.totalElements;
          this.pageSize = data.data.page.size;
          this.currentPage = data.data.page.number;
          this.totalPages = data.data.page.totalPages;
          this.calculatePageNumbers();
        }
      },
      error: (error) => {
        console.error('Error fetching categories', error);
        this.orders = [];
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
      this.loadOrders();
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
    this.loadOrders();
  }

  trackById(index: number, Order: OrderDTO): number {
    return Order.id;
  }

  setCategoryToToggle(id: number): void {
    this.idToToggle = id;
  }

  toggleStatus(): void {
    if (!this.idToToggle) return;
    this.loading = true;
    this.orderService.cancelOrder(this.idToToggle).subscribe({
      next: () => {
        this.loadOrders();
        this.closeModal('categoryToggleConfirmation');
      },
      error: (error) => {
        console.error('Error toggling status', error);
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      },
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
}
