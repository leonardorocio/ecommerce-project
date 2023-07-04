import { Observable } from 'rxjs';
import { Address } from './address';
import { Category } from './category';
import { OrderDetails, Order } from './order';
import { Product } from './product';
import { Shipment } from './shipment';
import { Shipper } from './shipper';
import { User } from './user';
import { AuthResponseBody } from './auth';

export interface APIDocs {
  tags: Tag[];
  paths: Path;
  components: Component;
}

export type Method = 'get' | 'put' | 'delete' | 'patch' | 'post';

export type IMethod = {
  [method in Method]: MethodProperties;
};

export interface Path {
  [key: string]: IMethod;
}

export interface MethodProperties {
  tags: string[];
  parameters: Parameters[];
  requestBody: {};
  operationId: string;
  summary: string;
  description: string;
}

export interface Parameters {
  name: string;
  schema: {
    type: string;
    default: string;
    format: string;
  };
}

export interface Tag {
  name: string;
  description: string;
}

export interface Schema {
  [key: string]: SchemaProperties;
}

export interface SchemaProperties {
  properties: PropertyAsKey;
  required: [];
}

export interface PropertyAsKey {
  [key: string]: PropertyAttributes;
}

export interface PropertyAttributes {
  type: string;
  description: string;
  format: string;
  example: string;
  $ref: string
}

export interface Component {
  schemas: Schema;
}
