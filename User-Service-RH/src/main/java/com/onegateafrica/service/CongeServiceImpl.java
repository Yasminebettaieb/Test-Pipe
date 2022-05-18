package com.onegateafrica.service;
import java.util.List;


import java.util.List;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import com.onegateafrica.repository.CongeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onegateafrica.dto.CongeDto;
import com.onegateafrica.entity.Conge;

@Service
public class CongeServiceImpl implements ServiceConge{
  private CongeRepository congeRepositoty;
  private ModelMapper mapper;
  @Autowired
  public CongeServiceImpl(CongeRepository congeRepositoty, ModelMapper mapper){
    this.congeRepositoty= congeRepositoty;
    this.mapper= mapper;
  }


  @Override
  public Conge create(CongeDto entity) {
    if(entity==null)
      throw new NoSuchElementException();
    return congeRepositoty.save(mapper.map(entity, Conge.class));
  }

  @Override
  public Conge update(long id, CongeDto entity) {
    var congeDb = getOne(id);
    if(entity.getTypeConge()!=null)
      congeDb.setTypeConge(entity.getTypeConge());
    if(entity.getRaison()!=null)
      congeDb.setRaison(entity.getRaison());
    if(entity.getDateDebut()!=null)
      congeDb.setDateDebut(entity.getDateDebut());
    if(entity.getDateFin()!=null)
      congeDb.setDateFin(entity.getDateFin());
    return congeRepositoty.save(congeDb);
  }

  @Override
  public boolean delete(long id) {
    var conge=congeRepositoty.findById(id);
    if (conge.isPresent())
    {
      congeRepositoty.deleteById(id);
      return true;
    }
    throw new NoSuchElementException("Conge doesn't exist");
  }

  @Override
  public Conge getOne(long id) throws EntityNotFoundException {
    return congeRepositoty.findById(id).orElseThrow(()->new NoSuchElementException("Conge doesn't exist"));
  }

  public List<Conge> getOneByIdUser(long id) throws EntityNotFoundException {
    return congeRepositoty.findCongeByIdUser(id);
  }

//
//  public List<Conge> getOneByIdUser(long id) throws EntityNotFoundException {
//    return congeRepositoty.findCongeByIdUser(id);
//  }


}
