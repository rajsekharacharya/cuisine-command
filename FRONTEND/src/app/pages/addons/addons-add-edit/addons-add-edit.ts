import { CommonModule } from '@angular/common';
import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  SimpleChanges,
  OnChanges
} from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AddonsRequestDTO, AddonsResponseDTO } from '../../../interfaces/addons-interface';
import { AddonsService } from '../../../service/service/addons-service';

@Component({
  selector: 'app-addons-add-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './addons-add-edit.html',
  styleUrl: './addons-add-edit.scss'
})
export class AddonsAddEdit implements OnInit, OnChanges {
  @Input() selectedAddons: AddonsResponseDTO | null = null;
  @Output() addonsSaved = new EventEmitter<void>();

  addonsForm!: FormGroup;
  loading = false;

  constructor(private fb: FormBuilder, private addonsService: AddonsService) { }

  ngOnInit(): void {
    this.initForm();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selectedAddons'] && this.selectedAddons) {
      this.setSelectedAddons(this.selectedAddons);
    } else {
      this.resetAddons();
    }
  }

  private initForm(): void {
    this.addonsForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      status: [true, Validators.required]
    });
  }

  private setSelectedAddons(addons: AddonsResponseDTO): void {
    this.addonsForm.patchValue({
      name: addons.name,
      description: addons.description,
      status: addons.status
    });
  }

  get isEditMode(): boolean {
    return !!this.selectedAddons?.id;
  }

  resetAddons(): void {
    this.selectedAddons = null;
    this.addonsForm.reset({
      name: '',
      description: '',
      status: true
    });
  }

  saveAddons(): void {
    if (this.addonsForm.invalid) return;

    this.loading = true;

    const dto: AddonsRequestDTO = { ...this.addonsForm.value };

    const request$ = this.isEditMode
      ? this.addonsService.updateAddons(this.selectedAddons!.id, dto)
      : this.addonsService.createAddons(dto);

    request$.subscribe({
      next: () => {
        this.addonsSaved.emit();
        this.resetAddons();
      },
      error: (err) => {
        console.error('Save failed:', err);
        alert('Failed to save add-on.');
      },
      complete: () => (this.loading = false)
    });
  }
}
