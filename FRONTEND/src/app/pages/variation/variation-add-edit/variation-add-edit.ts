import { CommonModule } from '@angular/common';
import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges
} from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { VariationRequestDTO, VariationResponseDTO } from '../../../interfaces/variations-interface';
import { VariationService } from '../../../service/service/variation-service';

@Component({
  selector: 'app-variation-add-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './variation-add-edit.html',
  styleUrl: './variation-add-edit.scss'
})
export class VariationAddEdit implements OnInit, OnChanges {
  @Input() selectedVariation: VariationResponseDTO | null = null;
  @Output() variationSaved = new EventEmitter<void>();

  variationForm!: FormGroup;
  loading = false;

  constructor(private fb: FormBuilder, private variationService: VariationService) {}

  ngOnInit(): void {
    this.initForm();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selectedVariation'] && this.selectedVariation) {
      this.setSelectedVariation(this.selectedVariation);
    } else {
      this.resetVariation();
    }
  }

  private initForm(): void {
    this.variationForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      status: [true, Validators.required]
    });
  }

  private setSelectedVariation(variation: VariationResponseDTO): void {
    this.variationForm.patchValue({
      name: variation.name,
      description: variation.description,
      status: variation.status
    });
  }

  get isEditMode(): boolean {
    return !!this.selectedVariation?.id;
  }

  resetVariation(): void {
    this.selectedVariation = null;
    this.variationForm.reset({
      name: '',
      description: '',
      status: true
    });
  }

  saveVariation(): void {
    if (this.variationForm.invalid) return;

    this.loading = true;
    const dto: VariationRequestDTO = { ...this.variationForm.value };

    const request$ = this.isEditMode
      ? this.variationService.updateVariation(this.selectedVariation!.id, dto)
      : this.variationService.createVariation(dto);

    request$.subscribe({
      next: () => {
        this.variationSaved.emit();
        this.resetVariation();
      },
      error: (err) => {
        console.error('Save failed:', err);
        alert('Failed to save variation.');
      },
      complete: () => {
        this.loading = false;
      }
    });
  }
}
