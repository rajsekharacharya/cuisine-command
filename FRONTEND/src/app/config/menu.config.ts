export const MENU_ITEMS = [
  {
    label: 'Dashboard',
    icon: 'icon-dashboard',
    route: '/dashboard',
    roles: ['ADMIN']
  },
  {
    label: 'User',
    icon: 'icon-user',
    route: '/user',
    roles: ['ADMIN']
  },
  {
    label: 'Master',
    icon: 'icon-master',
    isGroup: true,
    roles: ['ADMIN'],
    children: [
      {
        label: 'Restaurant',
        icon: 'icon-restaurant',
        route: '/master/restaurant',
        roles: ['ADMIN']
      },
      {
        label: 'Category',
        icon: 'icon-category',
        route: '/master/category',
        roles: ['ADMIN']
      },
      {
        label: 'Variation',
        icon: 'icon-variation',
        route: '/master/variation',
        roles: ['ADMIN']
      },
      {
        label: 'Addons',
        icon: 'icon-addons',
        route: '/master/addons',
        roles: ['ADMIN']
      }
    ]
  },
  {
    label: 'Item',
    icon: 'icon-item',
    route: '/item',
    roles: ['ADMIN']
  },
  {
    label: 'POS',
    icon: 'icon-pos',
    route: '/pos',
    roles: ['ADMIN', 'SERVER']
  },
  {
    label: 'Billing',
    icon: 'icon-billing',
    route: '/order',
    roles: ['ADMIN']
  },
  // {
  //   label: 'Report',
  //   icon: 'icon-report',
  //   route: '/report',
  //   roles: ['ADMIN']
  // },
  // {
  //   label: 'Settings',
  //   icon: 'icon-settings',
  //   route: '/settings',
  //   roles: ['ADMIN']
  // }
];
