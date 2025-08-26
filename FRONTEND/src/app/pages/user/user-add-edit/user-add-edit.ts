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
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import {
  RoleType,
  UsersRequestDTO,
  UsersResponseDTO,
} from '../../../interfaces/user-interface';
import { UserService } from '../../../service/service/user-service';

@Component({
  selector: 'app-user-add-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './user-add-edit.html',
  styleUrl: './user-add-edit.scss',
})
export class UserAddEdit implements OnInit, OnChanges {
  @Input() selectedUser: UsersResponseDTO | null = null;
  @Output() userSaved = new EventEmitter<void>();

  userForm!: FormGroup;
  roleList: { key: string; value: string }[] = [];
  loading = false;
  isProfileMode = false;

  constructor(
    private readonly fb: FormBuilder,
    private readonly router: Router,
    private readonly userService: UserService
  ) {}

  ngOnInit(): void {
    this.isProfileMode = this.router.url.includes('profile');
    this.initForm();
    this.roleList = Object.entries(RoleType).map(([key, value]) => ({
      key,
      value,
    }));
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selectedUser'] && this.selectedUser) {
      // Edit Mode – load form values and make password optional
      this.setFormFromUser(this.selectedUser);
      this.userForm.get('password')?.clearValidators(); // ❌ Not required in edit mode
    } else if (!this.selectedUser && this.userForm) {
      // Create Mode – reset form and require password
      this.userForm.reset({
        name: '',
        email: '',
        mobile: '',
        password: '',
        role: RoleType.ADMIN,
      });
      this.userForm.get('password')?.setValidators(Validators.required); // ✅ Required in create mode
    }

    // Always update validation state
    this.userForm.get('password')?.updateValueAndValidity();
  }

  private initForm(): void {
    this.userForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      username: ['', Validators.required],
      mobile: ['', Validators.required],
      password: [''],
      role: [RoleType.ADMIN, Validators.required],
    });
  }

  private setFormFromUser(user: UsersResponseDTO): void {
    this.userForm.patchValue({
      name: user.name,
      email: user.email,
      username: user.username,
      mobile: user.mobile,
      role: user.role,
      password: '',
    });
  }

  get isEditMode(): boolean {
    return !!this.selectedUser?.id;
  }

  resetUser(): void {
    this.selectedUser = null;
    this.userForm.reset({
      name: '',
      email: '',
      username: '',
      mobile: '',
      password: '',
      role: RoleType.ADMIN,
    });
  }

  saveUser(): void {
    if (this.userForm.invalid) return;

    this.loading = true;

    const formValue = this.userForm.value;

    const userDto: UsersRequestDTO = {
      name: formValue.name,
      email: formValue.email,
      username: formValue.username,
      mobile: formValue.mobile,
      role: formValue.role,
      ...(formValue.password ? { password: formValue.password } : {}),
    };

    const request$ = this.isEditMode
      ? this.userService.updateUser(this.selectedUser!.id, userDto)
      : this.userService.createUser(userDto);

    request$.subscribe({
      next: () => {
        this.userSaved.emit();
        this.resetUser();
      },
      error: (err) => {
        console.error('Save failed:', err);
        alert('Failed to save user.');
      },
      complete: () => {
        this.loading = false;
      },
    });
  }
}
