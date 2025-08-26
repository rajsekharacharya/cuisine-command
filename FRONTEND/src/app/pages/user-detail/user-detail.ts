import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-user-detail',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './user-detail.html',
  styleUrl: './user-detail.scss',
})
export class UserDetail implements OnInit {
userProfile: any = {
    photoUrl: null,
    isOnline: true,
    name: 'John Doe',
    role: 'Admin',
    lastLogin: new Date(),
    email: 'john.doe@example.com',
    phone: '+1234567890',
    dob: '1990-01-01',
    address: '123 Main St, City, Country'
  };

  isContactEditMode: boolean = false;
  contactForm!: FormGroup;
  savingContact: boolean = false;

  isPasswordEditMode: boolean = false;
  passwordForm!: FormGroup;
  savingPassword: boolean = false;

  showCurrentPassword: boolean = false;
  showNewPassword: boolean = false;
  showConfirmPassword: boolean = false;

  constructor(
    private fb: FormBuilder,
    // private userService: UserService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadUserProfile();
    this.initContactForm();
    this.initPasswordForm();
  }

  loadUserProfile(): void {
    // this.userService.getUserProfile().subscribe({
    //   next: (profile) => {
    //     this.userProfile = profile;
    //     this.contactForm.patchValue(profile);
    //   },
    //   error: (error) => {
    //     console.error('Error fetching profile:', error);
    //     this.showSnackbar('Error loading profile', 'snackbar-error');
    //   }
    // });
  }

  initContactForm(): void {
    this.contactForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern(/^\+?\d{10,15}$/)]],
      dob: [''],
      address: ['']
    });
  }

  initPasswordForm(): void {
    this.passwordForm = this.fb.group({
      currentPassword: ['', [Validators.required, Validators.minLength(8)]],
      newPassword: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', Validators.required]
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(group: FormGroup): any {
    const newPassword = group.get('newPassword')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    return newPassword === confirmPassword ? null : { mismatch: true };
  }

  toggleContactEditMode(): void {
    this.isContactEditMode = !this.isContactEditMode;
    if (this.isContactEditMode) {
      this.contactForm.patchValue(this.userProfile);
    } else {
      this.contactForm.reset();
    }
  }

  saveContact(): void {
    // if (this.contactForm.valid) {
    //   this.savingContact = true;
    //   const updatedProfile = { ...this.userProfile, ...this.contactForm.value };
    //   this.userService.updateProfile(updatedProfile).subscribe({
    //     next: () => {
    //       Object.assign(this.userProfile, updatedProfile);
    //       this.toggleContactEditMode();
    //       this.showSnackbar('Contact updated successfully', 'snackbar-success');
    //       this.savingContact = false;
    //     },
    //     error: (error) => {
    //       console.error('Error updating contact:', error);
    //       this.showSnackbar('Error updating contact', 'snackbar-error');
    //       this.savingContact = false;
    //     }
    //   });
    // }
  }

  uploadProfilePhoto(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.userProfile.photoUrl = e.target.result;
      };
      reader.readAsDataURL(file);

      // this.userService.uploadProfilePhoto(file).subscribe({
      //   next: (response) => {
      //     this.userProfile.photoUrl = response.url;
      //     this.showSnackbar('Photo uploaded successfully', 'snackbar-success');
      //   },
      //   error: (error) => {
      //     console.error('Error uploading photo:', error);
      //     this.showSnackbar('Error uploading photo', 'snackbar-error');
      //   }
      // });
    }
  }

  togglePasswordEditMode(): void {
    this.isPasswordEditMode = !this.isPasswordEditMode;
    if (!this.isPasswordEditMode) {
      this.passwordForm.reset();
    }
  }

  savePassword(): void {
    if (this.passwordForm.valid) {
      this.savingPassword = true;
      const { currentPassword, newPassword } = this.passwordForm.value;
      // this.userService.changePassword({ currentPassword, newPassword }).subscribe({
      //   next: () => {
      //     this.togglePasswordEditMode();
      //     this.showSnackbar('Password updated successfully', 'snackbar-success');
      //     this.savingPassword = false;
      //   },
      //   error: (error) => {
      //     console.error('Error updating password:', error);
      //     this.showSnackbar('Error updating password. Please check your current password.', 'snackbar-error');
      //     this.savingPassword = false;
      //   }
      // });
    }
  }

  toggleCurrentPasswordVisibility(): void {
    this.showCurrentPassword = !this.showCurrentPassword;
  }

  toggleNewPasswordVisibility(): void {
    this.showNewPassword = !this.showNewPassword;
  }

  toggleConfirmPasswordVisibility(): void {
    this.showConfirmPassword = !this.showConfirmPassword;
  }

  private showSnackbar(message: string, panelClass: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 5000,
      panelClass: [panelClass]
    });
  }
}
