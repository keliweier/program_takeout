// path-to-regexp.d.ts
import { Path, Key, Options } from 'path-to-regexp';

declare module 'path-to-regexp' {
  export interface PathFunctionOptions {
    pretty?: boolean;
  }

  export type PathFunction = (data?: object, options?: PathFunctionOptions) => string;

  export function compile(path: Path): PathFunction;
  export function compile(path: Path, options: Options): PathFunction;
}