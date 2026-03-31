import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { DocService} from '@core/services/docs/DocService';

export const endpointResolver: ResolveFn<any> = (route) => {
  const docService = inject(DocService);
  const id = route.paramMap.get('endpointId');

  if (!id) {
    return docService.getCategoryStructure();
  }

  return docService.getEndpointById(id);
};
