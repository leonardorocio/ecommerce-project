export interface Address {
  addressId: number,
  zipCode: string,
  city: string,
  state: string,
  streetWithNumber: string,
  userId: number
}

export interface CityResponse {
  id: number,
  nome: string
}

export interface StateResponse {
  id: number,
  nome: string,
  sigla: string
}
