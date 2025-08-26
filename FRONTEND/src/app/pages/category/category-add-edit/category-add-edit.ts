import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CategoryRequestDTO, CategoryResponseDTO } from '../../../interfaces/categories-interface';
import { CategoryService } from '../../../service/service/category-service';
import { apiUrl } from './../../../config/api-config';

@Component({
  selector: 'app-category-add-edit',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './category-add-edit.html',
  styleUrl: './category-add-edit.scss'
})
export class CategoryAddEdit implements OnInit, OnChanges {
  @Input() selectedCategory: CategoryResponseDTO | null = null;
  @Output() categorySaved = new EventEmitter<void>();
  @ViewChild('fileInput') fileInputRef!: ElementRef<HTMLInputElement>;

  categoryForm!: FormGroup;
  url = apiUrl;
  previewUrl: string | null = null;
  imageFile: File | null = null;
  loading = false;

  constructor(
    private readonly fb: FormBuilder,
    private readonly categoryService: CategoryService
  ) { }

  ngOnInit(): void {
    this.initForm();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selectedCategory']) {
      if (this.selectedCategory) {
        this.setSelectedCategory(this.selectedCategory);
      } else {
        this.resetCategory();
      }
    }
  }

  initForm(): void {
    this.categoryForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      status: [true, Validators.required]
    });
  }

  setSelectedCategory(category: CategoryResponseDTO): void {
    this.categoryForm.patchValue({
      name: category.name,
      description: category.description,
      status: category.status
    });
    this.imageFile = null;
    this.previewUrl = category.image ? `${this.url}/${category.image}` : null;
  }

  resetCategory(): void {
    this.selectedCategory = null;
    this.categoryForm.reset({
      name: '',
      description: '',
      status: true
    });
    this.imageFile = null;
    this.previewUrl = null;

    if (this.fileInputRef) {
      this.fileInputRef.nativeElement.value = '';
    }
  }

  get isEditMode(): boolean {
    return !!this.selectedCategory?.id;
  }

  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      if (file.size > 5 * 1024 * 1024) {
        alert('File size exceeds 5MB limit.');
        input.value = '';
        return;
      }
      this.imageFile = file;
      const reader = new FileReader();
      reader.onload = () => {
        this.previewUrl = reader.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  removeImage(): void {
    this.imageFile = null;
    this.previewUrl = null;
    if (this.fileInputRef) {
      this.fileInputRef.nativeElement.value = '';
    }
  }

  saveCategory(): void {
    if (this.categoryForm.invalid) return;

    this.loading = true;

    const categoryDto: CategoryRequestDTO = {
      name: this.categoryForm.value.name,
      description: this.categoryForm.value.description,
      status: this.categoryForm.value.status
    };

    const request$ = this.isEditMode
      ? this.categoryService.updateCategory(this.selectedCategory!.id, categoryDto, this.imageFile || undefined)
      : this.categoryService.createCategory(categoryDto, this.imageFile || undefined);

    request$.subscribe({
      next: () => {
        this.categorySaved.emit();
        this.resetCategory();
      },
      error: (err) => {
        console.error('Save failed:', err);
        alert('Failed to save category. Please try again.');
      },
      complete: () => {
        this.loading = false;
      }
    });
  }
}
