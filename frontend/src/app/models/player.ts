import { Bonus } from "./bonus";
import { Pixel } from "./pixel";

export interface Player{
    id: number;
    pseudo: string;
    password: string;
    age: number;
    countryCode: string;
    credits: number;
    creditsPerTick: number;

    pixels: Pixel[];
    bonuses: Bonus[];
    activeBonuses?: number[];
}