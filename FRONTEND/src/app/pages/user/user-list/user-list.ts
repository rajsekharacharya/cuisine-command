import { CommonModule, formatDate } from '@angular/common';
import {
  Component, ElementRef, EventEmitter, Input, OnChanges, OnInit,
  Output, SimpleChanges, ViewChild
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TeleportModalDirective } from '../../../directives/teleport-modal.directive';
import { UsersResponseDTO } from '../../../interfaces/user-interface';
import { UserService } from '../../../service/service/user-service';
import { Subject, debounceTime } from 'rxjs';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [TeleportModalDirective, FormsModule, CommonModule],
  templateUrl: './user-list.html',
  styleUrl: './user-list.scss'
})
export class UserList implements OnInit, OnChanges {

  @Input() searchTerm: string = '';
  @Input() pageSizeInput: number = 5;
  @Output() userSelected = new EventEmitter<UsersResponseDTO>();
  @Output() userSaved = new EventEmitter<void>();
  @Output() userDeleted = new EventEmitter<void>();

  @ViewChild('triggerButton', { static: false }) triggerButton!: ElementRef;

  users: UsersResponseDTO[] = [];
  selectedUser: UsersResponseDTO | null = null;
  selectedRowId: number | null = null;
  idToDelete: number | null = null;

  loaderRows = Array(8).fill(0);
  loaderCells = Array(7).fill(0);

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
  pageSizeOptions: number[] = [5, 10, 20, 50, 100];

  constructor(
    private readonly userService: UserService,
    private readonly eRef: ElementRef
  ) {}

  ngOnInit(): void {
    this.loadUsers();

    this.searchSubject.pipe(debounceTime(300)).subscribe(() => {
      this.currentPage = 0;
      this.loadUsers();
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
      this.loadUsers();
    }
  }

  formatDate(value: Date | string, format: string, locale: string): string {
    return formatDate(value, format, locale);
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
    this.loadUsers();
  }

  trackById(index: number, user: UsersResponseDTO): number {
    return user.id;
  }
  loadUsers(showLoading: boolean = true): void {
    this.loading = showLoading;
    const params = {
      page: this.currentPage,
      size: this.pageSize,
      sort: `${this.sortField},${this.sortDirection}`,
      search: this.searchQuery.trim(),
      searchField: this.searchField,
      unpaged: this.unpaged
    };

    this.userService.getAllUsers(params).subscribe({
      next: (data) => {
        if (data.status && data.data) {
          this.users = data.data.content ?? [];
          this.totalItems = data.data.page.totalElements;
          this.pageSize = data.data.page.size;
          this.currentPage = data.data.page.number;
          this.totalPages = data.data.page.totalPages;
          this.calculatePageNumbers();
        }
      },
      error: (error) => {
        console.error('Error fetching users', error);
        this.users = [];
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
      this.loadUsers();
    }
  }

  editUser(user: UsersResponseDTO): void {
    this.selectedUser = user;
    this.selectedRowId = user.id;
    this.userSelected.emit(user);
  }

  setUserToDelete(id: number): void {
    this.idToDelete = id;
  }

  closeModal(modalId: string): void {
    const modal = document.getElementById(modalId);
    if (modal) {
      const modalInstance = (window as any).bootstrap.Modal.getInstance(modal);
      if (modalInstance) modalInstance.hide();
    }
    this.idToDelete = null;
    this.triggerButton?.nativeElement?.focus();
  }

  onUserSaved(): void {
    this.loadUsers(false);
    this.userSaved.emit();
    this.resetForm();
  }

  resetForm(): void {
    this.selectedUser = null;
    this.selectedRowId = null;
  }
}
