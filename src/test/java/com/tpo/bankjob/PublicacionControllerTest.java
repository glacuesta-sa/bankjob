package com.tpo.bankjob;

import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.repository.PublicacionRepository;
import com.tpo.bankjob.model.vo.EmpresaVO;
import com.tpo.bankjob.model.vo.ModalidadEnum;
import com.tpo.bankjob.model.vo.PublicacionVO;
import com.tpo.bankjob.model.vo.TipoTrabajoEnum;

@SpringBootTest
public class PublicacionControllerTest {
	
    @Autowired
    Publicacion publicacion;
    
    @Autowired
    EmpresaRepository empresaRepository;
    
    @Autowired
    PublicacionRepository publicacionRepository;
		
	@Test
	public void givenValidEmpresaAndPublicacionVoWhenAddPublicacionThenSaveItSucessfully() {
		
		// given
		EmpresaVO empresaVO = new EmpresaVO("Empresa1");
		
		empresaRepository.saveAndFlush(empresaVO);
		
		PublicacionVO publicacionVO = new PublicacionVO(empresaVO.getId(),
				"Publicacion1", 
				"Descripcion", 
				ModalidadEnum.FULL_TIME, 
				TipoTrabajoEnum.PRESENCIAL, 
				"Lugar",
				"Categoria",
				Double.valueOf(100));
		
		//  when
		publicacion.addPublicacion(publicacionVO);
		
		// then
		EmpresaVO resultEmpresa = null;
		PublicacionVO resultPublicacion = null;
		
		Optional<EmpresaVO> optResultEmpresa = empresaRepository.findById(empresaVO.getId());
		Optional<PublicacionVO> optResultPublicacion = publicacionRepository.findById(publicacionVO.getId());
		
		if(optResultEmpresa.isPresent())
			resultEmpresa = optResultEmpresa.get();
		
		if(optResultPublicacion.isPresent())
			resultPublicacion = optResultPublicacion.get();
		
		Assert.assertNotNull(resultEmpresa);
		Assert.assertNotNull(resultPublicacion);
		Assert.assertTrue(empresaRepository.findById(empresaVO.getId()).get().getPublicaciones().get(0).equals(resultPublicacion));
		Assert.assertTrue(resultPublicacion.getIdEmpresa().equals(empresaVO.getId()));
	}
}
