package com.onegateafrica.service;


import javax.persistence.EntityNotFoundException;

import com.onegateafrica.dto.CongeDto;
import com.onegateafrica.entity.Conge;


public interface ServiceConge {

  Conge create(CongeDto entity);
  Conge update(long id, CongeDto entity) ;
  boolean           delete(long id)  ;
  Conge getOne(long id) throws EntityNotFoundException;

}
