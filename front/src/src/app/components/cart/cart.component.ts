import {Component, OnInit} from '@angular/core';
import {Observable, switchMap, tap} from "rxjs";
import {OrderService} from "../../services/order.service";
import {IOrder} from "../../models/order";
import {IProduct} from "../../models/product";
import {CatalogService} from "../../services/catalog.service";
import {IOrderItem} from "../../models/orderItem";
import {CartService} from "../../services/cart.service";
import {ICartItem} from "../../models/cartItem";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css', '../../../styles.css']
})
export class CartComponent implements OnInit {
  title = 'Корзина';
  loading = false
  products$: Observable<IProduct[]> = new Observable<IProduct[]>()
  cartItems$: Observable<ICartItem[]> = new Observable<ICartItem[]>()
  userId = "2"
  deliveryAddress: string = ''
  deliveryType: string = ''
  price: number = 0
  isNotEnough: boolean = true

  dummyProducts: IProduct[] = [
    {
      id: '1',
      name: 'Product 1',
      price: 10,
      description: 'Description 1',
      image: 'image1.jpg',
      count: 5,
      attributes: [],
      isAvailable: true,
      availableQuantity: 10
    },
    {
      id: '2',
      name: 'Product 2',
      price: 20,
      description: 'Description 2',
      image: 'image2.jpg',
      count: 3,
      attributes: [],
      isAvailable: true,
      availableQuantity: 8
    }
  ];

  dummyCartItems: ICartItem[] = [
    {
      product_id: '1',
      count: 2
    },
    {
      product_id: '2',
      count: 1
    }
  ];
  setPrice(price: number) {
    this.price = price
  }

  increasePrice(price: number) {
    this.price += price
  }

  decreasePrice(price: number) {
    this.price -= price
  }

  onSubmit() {

    // Обработка отправки формы
    // Выполнение действий для оформления заказа
  }
  constructor(private cartService: CartService, private catalogService: CatalogService) {
  }
  refreshCartItems = () => {
    this.loading = true;
    console.log("suka");
    this.cartItems$ = this.cartService.getAll(this.userId).pipe(
      tap(() => {
        console.log("dadadada");
        this.loading = false;
      })
    );
    this.cartItems$.subscribe((cartItems) => {
      this.products$ = this.catalogService.getSome(cartItems.map((item: ICartItem) => item.product_id)).pipe(
        tap((sas) => {
          this.loading = false;
        })
      );
    });
  }

  ngOnInit(): void {
    this.products$ = new Observable<IProduct[]>(subscriber => {
      subscriber.next(this.dummyProducts);
      subscriber.complete();
    });

    this.cartItems$ = new Observable<ICartItem[]>(subscriber => {
      subscriber.next(this.dummyCartItems);
      subscriber.complete();
    });
  }
}
