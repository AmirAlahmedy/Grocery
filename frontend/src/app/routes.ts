import {Routes} from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {ProductDetailsComponent} from './pages/product-details/product-details.component';

const routeConfig: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'Home page',
  },
  {
    path: 'products/:id',
    component: ProductDetailsComponent,
    title: 'Product details',
  },
];

export default routeConfig;
