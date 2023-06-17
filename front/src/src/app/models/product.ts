import {IAttribute} from "./attribute"

export interface IProduct {
  id: string
  name: string
  price: number
  description: string
  image: string
  count: number
  attributes: IAttribute[];
  isAvailable: boolean;
  availableQuantity: number;
}
