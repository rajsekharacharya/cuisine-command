import { environment } from '../../environments/environment';

export const apiUrl = environment.apiUrl;

export const ApiEndpoint = {
  Auth: {
    login: `${apiUrl}/auth/login`,
    logout: `${apiUrl}/auth/logout`,
    refresh: `${apiUrl}/auth/refresh`,
  },
  Users: {
    create: `${apiUrl}/api/admin/users`,
    update: (userId: number) => `${apiUrl}/api/admin/users/${userId}`,
    getAll: `${apiUrl}/api/admin/users`,
    getById: (userId: number) => `${apiUrl}/api/admin/users/${userId}`,
    toggleStatus: (userId: number) =>
      `${apiUrl}/api/admin/users/${userId}/toggle-status`,
    verifyEmail: `${apiUrl}/api/admin/users/verify`,
  },
  Restaurants: {
    create: `${apiUrl}/api/master/restaurants`,
    update: (restaurantId: number) =>
      `${apiUrl}/api/master/restaurants/${restaurantId}`,
    getAll: `${apiUrl}/api/master/restaurants`,
    getById: (restaurantId: number) =>
      `${apiUrl}/api/master/restaurants/${restaurantId}`,
    toggleStatus: (restaurantId: number) =>
      `${apiUrl}/api/master/restaurants/${restaurantId}/toggle-status`,
  },
  Variations: {
    create: `${apiUrl}/api/master/variations`,
    update: (variationId: number) =>
      `${apiUrl}/api/master/variations/${variationId}`,
    getAll: `${apiUrl}/api/master/variations`,
    getById: (variationId: number) =>
      `${apiUrl}/api/master/variations/${variationId}`,
    toggleStatus: (variationId: number) =>
      `${apiUrl}/api/master/variations/${variationId}/toggle-status`,
  },
  Items: {
    create: `${apiUrl}/api/master/items`,
    update: (itemId: number) => `${apiUrl}/api/master/items/${itemId}`,
    getAll: `${apiUrl}/api/master/items`,
    getById: (itemId: number) => `${apiUrl}/api/master/items/${itemId}`,
    toggleStatus: (itemId: number) =>
      `${apiUrl}/api/master/items/${itemId}/toggle-status`,
    getPosData: `${apiUrl}/api/master/items/pos`,
  },
  Categories: {
    create: `${apiUrl}/api/master/categories`,
    update: (categoryId: number) =>
      `${apiUrl}/api/master/categories/${categoryId}`,
    getAll: `${apiUrl}/api/master/categories`,
    getById: (categoryId: number) =>
      `${apiUrl}/api/master/categories/${categoryId}`,
    toggleStatus: (categoryId: number) =>
      `${apiUrl}/api/master/categories/${categoryId}/toggle-status`,
  },
  Addons: {
    create: `${apiUrl}/api/master/addons`,
    update: (addonsId: number) => `${apiUrl}/api/master/addons/${addonsId}`,
    getAll: `${apiUrl}/api/master/addons`,
    getById: (addonsId: number) => `${apiUrl}/api/master/addons/${addonsId}`,
    toggleStatus: (addonsId: number) =>
      `${apiUrl}/api/master/addons/${addonsId}/toggle-status`,
  },
  Orders: {
    getAllOrders: `${apiUrl}/api/pos/orders`,
    create: `${apiUrl}/api/pos/orders`,
    update: (orderId: number) => `${apiUrl}/api/pos/orders/${orderId}`,
    getById: (orderId: number) => `${apiUrl}/api/pos/orders/${orderId}`,
    getHeldOrders: `${apiUrl}/api/pos/orders/held`,
    finalize: (orderId: number) =>
      `${apiUrl}/api/pos/orders/${orderId}/finalize`,
    cancel: (orderId: number) => `${apiUrl}/api/pos/orders/${orderId}/cancel`,
  },
  Customer: {
    create: `${apiUrl}/api/pos/customers`,
    update: (customerId: number) => `${apiUrl}/api/pos/customers/${customerId}`,
    getAll: `${apiUrl}/api/pos/customers`,
    getById: (customerId: number) =>
      `${apiUrl}/api/pos/customers/${customerId}`,
  },
  Dashboard: {
    getDashboardData: `${apiUrl}/api/dashboard`,
  },
};
