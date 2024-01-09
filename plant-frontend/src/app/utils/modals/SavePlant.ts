export interface SavePlant {
    name: string;
    description: string;
    price: number;
    water: string;
    temperature: string;
    pictureData: File | Blob | null; // Updated to be of type File or Blob
    onHandValue: number;
  }
  