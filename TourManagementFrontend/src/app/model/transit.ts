export class Transit {
  id!: bigint
  busId!: bigint
  departureFrom!: string
  arrivalTo!: string
  departure!: Date
  arrival!: Date
  ticketPrice!: number
}
