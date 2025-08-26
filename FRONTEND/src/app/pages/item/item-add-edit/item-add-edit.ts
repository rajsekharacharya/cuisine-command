import { CommonModule } from '@angular/common';
import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import {
  Dietary,
  GstType,
  ItemRequestDTO,
  ItemResponseDTO,
  OrderType,
} from '../../../interfaces/items-interface';
import { ToastService } from '../../../service/common/toast-service';
import { AddonsService } from '../../../service/service/addons-service';
import { CategoryService } from '../../../service/service/category-service';
import { ItemService } from '../../../service/service/item-service';
import { VariationService } from '../../../service/service/variation-service';

@Component({
  selector: 'app-item-add-edit',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, NgSelectModule],
  templateUrl: './item-add-edit.html',
  styleUrl: './item-add-edit.scss',
})
export class ItemAddEdit implements OnInit, OnChanges {
  @Input() selectedItem: ItemResponseDTO | null = null;
  @Output() itemSaved = new EventEmitter<void>();

  itemForm!: FormGroup;

  categories: any[] = [];
  availableAddons: string[] = [];
  availableVariations: any[] = [];
  availableComboItems: any[] = [];

  dietaryOptions = Object.values(Dietary);
  orderTypeOptions = Object.values(OrderType);
  gstTypeOptions = Object.values(GstType);

  loading = false;

  constructor(
    private fb: FormBuilder,
    private itemService: ItemService,
    private categoryService: CategoryService,
    private addonsService: AddonsService,
    private variationService: VariationService,
    private toast: ToastService
  ) {}

  ngOnInit() {
    this.buildForm();
    this.loadCategories();
    this.loadAddons();
    this.loadVariations();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selectedItem'] && this.selectedItem) {
      this.setSelectedItem(this.selectedItem);
    } else {
      this.buildForm();
    }
  }

  asFormArray(control: AbstractControl | null): FormArray {
    return control as FormArray;
  }

  buildForm() {
    this.itemForm = this.fb.group({
      categoryId: [null, Validators.required],
      name: ['', Validators.required],
      shortCode: [''],
      price: [0],
      description: [''],
      dietary: [Dietary.VEG, Validators.required],
      gstType: [GstType.EXEMPTED],
      orderTypes: this.fb.group({
        [OrderType.DINE_IN]: [true],
        [OrderType.PICKUP]: [true],
        [OrderType.DELIVERY]: [true],
      }),
      variation: [false],
      addons: [false],
      combo: [false],
      status: [true],
      variations: this.fb.array([]),
      itemAddons: this.fb.array([]),
    });
  }

  resetForm() {
    this.selectedItem = null;
    this.buildForm(); // Rebuild the form to reset to initial state
  }

  get variations(): FormArray {
    return this.itemForm.get('variations') as FormArray;
  }

  get itemAddons(): FormArray {
    return this.itemForm.get('itemAddons') as FormArray;
  }

  addVariation() {
    this.variations.push(
      this.fb.group({
        id: [null], // <--- required to check in template
        name: ['', Validators.required],
        price: [0],
        status: [true],
        addons: [false],
        itemAddons: this.fb.array([]),
      })
    );
  }

  removeVariation(index: number) {
    this.variations.removeAt(index);
  }

  addVariationAddon(variationIndex: number) {
    const addons = this.variations
      .at(variationIndex)
      .get('itemAddons') as FormArray;
    addons.push(
      this.fb.group({
        id: [null], // <--- required to check in template
        name: ['', Validators.required],
        price: [0],
        status: [true],
      })
    );
  }

  removeVariationAddon(variationIndex: number, addonIndex: number) {
    const addons = this.variations
      .at(variationIndex)
      .get('itemAddons') as FormArray;
    addons.removeAt(addonIndex);
  }

  addItemAddon() {
    this.itemAddons.push(
      this.fb.group({
        name: ['', Validators.required],
        price: [0],
        status: [true],
      })
    );
  }

  removeItemAddon(index: number) {
    this.itemAddons.removeAt(index);
  }

  setSelectedItem(item: ItemResponseDTO): void {
    this.itemForm.patchValue({
      categoryId: item.categoryId,
      name: item.name,
      shortCode: item.shortCode ?? '',
      price: item.price ?? 0,
      description: item.description ?? '',
      dietary: item.dietary,
      gstType: item.gstType ?? GstType.EXEMPTED,
      variation: item.variation,
      addons: item.addons,
      combo: item.combo,
      status: item.status,
    });

    // OrderTypes
    const orderTypesGroup = this.itemForm.get('orderTypes') as FormGroup;
    item.orderTypes.forEach((type) => {
      orderTypesGroup.get(type)?.setValue(true);
    });

    // Variations
    item.variations.forEach((v) => {
      const varGroup = this.fb.group({
        id: [v.id ?? null], // <-- add this line
        name: [v.name, Validators.required],
        price: [v.price ?? 0],
        status: [v.status],
        addons: [v.addons],
        itemAddons: this.fb.array([]),
      });

      v.itemAddons.forEach((a) => {
        (varGroup.get('itemAddons') as FormArray).push(
          this.fb.group({
            id: [a.id ?? null], // <-- add this line
            name: [a.name],
            price: [a.price ?? 0],
            status: [a.status],
          })
        );
      });

      this.variations.push(varGroup);
    });

    // Add-ons
    item.itemAddons.forEach((a) => {
      this.itemAddons.push(
        this.fb.group({
          id: [a.id ?? null], // <-- add this line
          name: [a.name],
          price: [a.price ?? 0],
          status: [a.status],
        })
      );
    });
  }

  get isEditMode(): boolean {
    return !!this.selectedItem?.id;
  }

  loadCategories() {
    this.categoryService.getAllCategories({ status: true }).subscribe({
      next: (res) => {
        this.categories = res.data?.content ?? [];
      },
    });
  }

  loadAddons() {
    this.addonsService.getAllAddons({ status: true, unpaged: true }).subscribe({
      next: (res) => {
        this.availableAddons = res.data?.content?.map((a) => a.name) ?? [];
      },
    });
  }

  loadVariations() {
    this.variationService
      .getAllVariations({ status: true, unpaged: true })
      .subscribe({
        next: (res) => {
          this.availableVariations = res.data?.content ?? [];
        },
      });
  }

  saveItem() {
    if (this.itemForm.invalid) return;
    this.loading = true;

    const formValue = this.itemForm.value;
    const orderTypes: OrderType[] = this.orderTypeOptions.filter(
      (type) => formValue.orderTypes[type]
    );

    const dto: ItemRequestDTO = {
      categoryId: formValue.categoryId,
      name: formValue.name,
      shortCode: formValue.shortCode,
      price: formValue.price,
      description: formValue.description,
      dietary: formValue.dietary,
      gstType: formValue.gstType,
      orderTypes,
      variation: formValue.variation,
      addons: formValue.addons,
      combo: formValue.combo,
      status: formValue.status,
      variations: formValue.variations,
      itemAddons: formValue.itemAddons,
    };

    const request$ = this.isEditMode
      ? this.itemService.updateItem(this.selectedItem!.id, dto)
      : this.itemService.createItem(dto);

    request$.subscribe({
      next: () => {
        this.itemSaved.emit();
        this.resetForm();
      },
      error: (err) => {
        this.toast.showError(err);
      },
      complete: () => {
        this.loading = false;
      },
    });
  }
}
