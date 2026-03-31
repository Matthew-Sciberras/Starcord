import { Injectable } from '@angular/core';
import { API_STRUCTURE } from '../../../../assets/data/endpoints.config';
import { ApiCategory, ApiEndpoint } from '@shared/models/api-docs.model';

@Injectable({
  providedIn: 'root'
})
export class DocService {
  private readonly _structure: ApiCategory[] = API_STRUCTURE;

  constructor() {}

  getCategoryStructure(): ApiCategory[] {
    return this._structure;
  }

  getCategoryByName(name: string): ApiCategory | null {
    return this._structure.find(
      cat => cat.name.toLowerCase() === name.toLowerCase()
    ) || null;
  }

  getEndpointById(id: string): (ApiEndpoint & { fullUrl: string }) | null {
    for (const category of this._structure) {
      const found = category.endpoints.find(e => e.id === id);
      if (found) {
        return {
          ...found,
          fullUrl: `${category.basePath}${found.path}`
        };
      }
    }
    return null;
  }

  getAllEndpoints(): ApiEndpoint[] {
    return this._structure.flatMap(cat => cat.endpoints);
  }
}
