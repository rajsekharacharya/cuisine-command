import { Routes } from '@angular/router';
import { AdminDashboard } from './pages/dashboard/admin-dashboard/admin-dashboard';
import { EmailVerifier } from './pages/email-verifier/email-verifier';
import { Forbidden } from './pages/forbidden/forbidden';
import { Layout } from './pages/layout/layout';
import { Login } from './pages/login/login';
import { User } from './pages/user/user';
import { authGuard } from './service/auth/auth.guard';
import { Restaurant } from './pages/restaurant/restaurant';
import { Category } from './pages/category/category';
import { Variation } from './pages/variation/variation';
import { Addons } from './pages/addons/addons';
import { Item } from './pages/item/item';
import { Pos } from './pages/pos/pos';
import { authGuardChild } from './service/auth/auth-guard-child-guard';
import { ServerLayout } from './pages/server-layout/server-layout';
import { Order } from './pages/order/order';
import { UserDetail } from './pages/user-detail/user-detail';

export const routes: Routes = [
  // Public routes
  { path: 'login', component: Login, data: { title: 'Login' } },
  { path: 'verify', component: EmailVerifier, data: { title: 'Email Verification' } },
  { path: 'forbidden', component: Forbidden, data: { title: 'Access Denied' } },

  {
    path: 'sale',
    component: ServerLayout,
    canActivate: [authGuard],
    canActivateChild: [authGuardChild],
    children: [
      { path: 'pos', component: Pos, data: { title: 'POS', roles: ['SERVER'] }, pathMatch: 'full' },  // accessible to both admin & user
    ]
  },

  // Protected routes with roles
  {
    path: '',
    component: Layout,
    canActivate: [authGuard],
    canActivateChild: [authGuardChild],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }, // default redirect; we’ll handle redirect logic after login separately
      { path: 'dashboard', component: AdminDashboard, data: { title: 'Dashboard', roles: ['ADMIN'] } },
      { path: 'user', component: User, data: { title: 'User', roles: ['ADMIN'] } },
      {
        path: 'master', children: [
          { path: 'restaurant', component: Restaurant, data: { title: 'Restaurant', roles: ['ADMIN'] } },
          { path: 'category', component: Category, data: { title: 'Category', roles: ['ADMIN'] } },
          { path: 'variation', component: Variation, data: { title: 'Variation', roles: ['ADMIN'] } },
          { path: 'addons', component: Addons, data: { title: 'Addons', roles: ['ADMIN'] } },
        ]
      },
      { path: 'item', component: Item, data: { title: 'Item', roles: ['ADMIN'] } },
      { path: 'pos', component: Pos, data: { title: 'POS', roles: ['ADMIN', 'USER'] } },  // accessible to both admin & user
      { path: 'order', component: Order, data: { title: 'Order', roles: ['ADMIN'] } },  // accessible to both admin & user
      { path: 'user-detail', component: UserDetail, data: { title: 'User Detail' } },  // accessible to both admin & user
    ]
  },

  // Wildcard fallback
  { path: '**', redirectTo: 'login' }
];

