import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  OnChanges,
  Output,
  SimpleChanges,
  ViewChild,
  ElementRef
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RestaurantRequestDTO, RestaurantResponseDTO } from '../../../interfaces/restaurants-interface';
import { RestaurantService } from '../../../service/service/restaurant-service';
import { apiUrl } from '../../../config/api-config';

@Component({
  selector: 'app-restaurant-add-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './restaurant-add-edit.html',
  styleUrl: './restaurant-add-edit.scss'
})
export class RestaurantAddEdit implements OnInit, OnChanges {
  @Input() selectedRestaurant: RestaurantResponseDTO | null = null;
  @Output() restaurantSaved = new EventEmitter<void>();

  @ViewChild('logoInput') logoInputRef!: ElementRef<HTMLInputElement>;
  @ViewChild('bannerInput') bannerInputRef!: ElementRef<HTMLInputElement>;

  restaurantForm!: FormGroup;

  logoFile: File | null = null;
  bannerFile: File | null = null;

  logoPreviewUrl: string | null = null;
  bannerPreviewUrl: string | null = null;

  loading = false;
  url = apiUrl;

  constructor(private fb: FormBuilder, private restaurantService: RestaurantService) {}

  ngOnInit(): void {
    this.initForm();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selectedRestaurant'] && this.selectedRestaurant) {
      this.setFormFromSelected(this.selectedRestaurant);
    } else if (!this.selectedRestaurant) {
      this.resetRestaurant();
    }
  }

  private initForm(): void {
    this.restaurantForm = this.fb.group({
      name: ['', Validators.required],
      cuisine: [''],
      description: [''],
      address: [''],
      contact: [''],
      status: [true, Validators.required]
    });
  }

  private setFormFromSelected(data: RestaurantResponseDTO): void {
    this.restaurantForm.patchValue({
      name: data.name,
      cuisine: data.cuisine,
      description: data.description,
      address: data.address,
      contact: data.contact,
      status: data.status
    });

    this.logoFile = null;
    this.bannerFile = null;

    this.logoPreviewUrl = data.logo ? `${this.url}/${data.logo}` : null;
    this.bannerPreviewUrl = data.banner ? `${this.url}/${data.banner}` : null;
  }

  get isEditMode(): boolean {
    return !!this.selectedRestaurant?.id;
  }

  onFileChange(event: Event, field: 'logo' | 'banner'): void {
    const input = event.target as HTMLInputElement;
    if (input.files?.length) {
      const file = input.files[0];

      if (file.size > 5 * 1024 * 1024) {
        alert('File size exceeds 5MB limit.');
        input.value = '';
        return;
      }

      const reader = new FileReader();
      reader.onload = () => {
        if (field === 'logo') {
          this.logoFile = file;
          this.logoPreviewUrl = reader.result as string;
        } else {
          this.bannerFile = file;
          this.bannerPreviewUrl = reader.result as string;
        }
      };
      reader.readAsDataURL(file);
    }
  }

  removeImage(field: 'logo' | 'banner') {
    if (field === 'logo') {
      this.logoFile = null;
      this.logoPreviewUrl = null;
      if (this.logoInputRef) this.logoInputRef.nativeElement.value = '';
    } else {
      this.bannerFile = null;
      this.bannerPreviewUrl = null;
      if (this.bannerInputRef) this.bannerInputRef.nativeElement.value = '';
    }
  }

  resetRestaurant(): void {
    this.selectedRestaurant = null;
    this.restaurantForm.reset({
      name: '',
      cuisine: '',
      description: '',
      address: '',
      contact: '',
      status: true
    });
    this.logoFile = null;
    this.bannerFile = null;
    this.logoPreviewUrl = null;
    this.bannerPreviewUrl = null;
    if (this.logoInputRef) this.logoInputRef.nativeElement.value = '';
    if (this.bannerInputRef) this.bannerInputRef.nativeElement.value = '';
  }

  saveRestaurant(): void {
    if (this.restaurantForm.invalid) return;

    this.loading = true;

    const dto: RestaurantRequestDTO = { ...this.restaurantForm.value };

    const request$ = this.isEditMode
      ? this.restaurantService.updateRestaurant(
          this.selectedRestaurant!.id,
          dto,
          this.logoFile || undefined,
          this.bannerFile || undefined
        )
      : this.restaurantService.createRestaurant(dto, this.logoFile || undefined, this.bannerFile || undefined);

    request$.subscribe({
      next: () => {
        this.restaurantSaved.emit();
        this.resetRestaurant();
      },
      error: (err) => {
        console.error('Save failed:', err);
        alert('Failed to save restaurant.');
      },
      complete: () => (this.loading = false)
    });
  }
}
