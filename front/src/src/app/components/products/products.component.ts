import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IProduct } from '../../models/product';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html'
})
export class ProductsComponent implements OnInit {
  title = 'Каталог';
  loading = false;
  products: IProduct[] = [
    {
      id: '1',
      name: 'Яблоки',
      price: 100,
      description: 'Описание товара 1',
      image: 'https://www.novochag.ru/upload/img_cache/65e/65e49477ede5af501592f7cd8830dda1_ce_1802x1200x28x0_cropped_1332x888.jpg',
      count: 10,
      availableQuantity: 10,
      isAvailable: true,
      attributes: [
        { name: 'Атрибут 1', description: 'Описание атрибута 1' },
        { name: 'Атрибут 2', description: 'Описание атрибута 2' }
      ]
    },
    {
      id: '2',
      name: 'Апельсины',
      price: 200,
      description: 'Описание товара 2',
      image: 'https://kartinkin.net/uploads/posts/2022-02/thumbs/1645500588_61-kartinkin-net-p-kvadratnie-kartinki-63.jpg',
      count: 0,
      availableQuantity: 0,
      isAvailable: false,
      attributes: [
        { name: 'Атрибут 3', description: 'Описание атрибута 3' },
        { name: 'Атрибут 4', description: 'Описание атрибута 4' }
      ]
    },
    {
      id: '3',
      name: 'Сливы',
      price: 400,
      description: 'Описание товара 2',
      image: 'https://kartinkin.net/uploads/posts/2022-02/thumbs/1645500588_61-kartinkin-net-p-kvadratnie-kartinki-63.jpg',
      count: 0,
      availableQuantity: 1,
      isAvailable: true,
      attributes: [
        { name: 'Атрибут 3', description: 'Описание атрибута 3' },
        { name: 'Атрибут 4', description: 'Описание атрибута 4' }
      ]
    },
    {
      id: '4',
      name: 'Бананы',
      price: 300,
      description: 'Описание товара 2',
      image: 'https://kartinkin.net/uploads/posts/2022-02/thumbs/1645500588_61-kartinkin-net-p-kvadratnie-kartinki-63.jpg',
      count: 0,
      availableQuantity: 124,
      isAvailable: true,
      attributes: [
        { name: 'Атрибут 3', description: 'Описание атрибута 3' },
        { name: 'Атрибут 4', description: 'Описание атрибута 4' }
      ]
    }
  ];
  products$: Observable<IProduct[]> = new Observable<IProduct[]>();

  ngOnInit(): void {
    this.products$ = new Observable<IProduct[]>((observer) => {
      observer.next(this.sortProducts('name'));
      observer.complete();
    });

    this.products$.subscribe((products) => {
      console.log(products);
    });
  }

  public sortProducts(sortBy: string): IProduct[] {
    return this.products.sort((a, b) => {
      if (sortBy === 'name') {
        return a.name.localeCompare(b.name);
      } else if (sortBy === 'price') {
        return a.price - b.price;
      } else if (sortBy === 'id') {
        return a.id.localeCompare(b.id);
      }
      return 0;
    });
  }

  toggleSortDirection(sortBy: string): void {
    this.products$ = new Observable<IProduct[]>((observer) => {
      observer.next(this.sortProducts(sortBy).reverse());
      observer.complete();
    });
  }
}
