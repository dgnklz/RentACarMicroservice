package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryService.business.abstracts.BrandService;
import com.kodlamaio.inventoryService.business.abstracts.ModelService;
import com.kodlamaio.inventoryService.business.constants.MessagesForModel;
import com.kodlamaio.inventoryService.business.requests.create.CreateModelRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateModelRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateModelResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllModelsResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetModelResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateModelResponse;
import com.kodlamaio.inventoryService.dataAccess.ModelRepository;
import com.kodlamaio.inventoryService.entities.Brand;
import com.kodlamaio.inventoryService.entities.Model;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ModelManager implements ModelService{
	private ModelRepository modelRepository;
	private ModelMapperService modelMapperService;
	private BrandService brandService;

	@Override
	public List<GetAllModelsResponse> getAll() {
		List<Model> models = modelRepository.findAll();
		List<GetAllModelsResponse> responses = models.stream()
				.map(model -> this.modelMapperService.forResponse().map(model, GetAllModelsResponse.class))
				.collect(Collectors.toList());
		return responses;
	}
	
	@Override
	public GetModelResponse getModelById(String id) {
		checkIfModelExistsById(id);
		Model model = modelRepository.findById(id).get();
		
		GetModelResponse response = modelMapperService.forResponse().map(model, GetModelResponse.class);
		return response;
	}
	
	@Override
	public Model getModelNameByModelId(String id) {
		checkIfModelExistsById(id);
		
		Model model = modelRepository.findById(id).orElse(null);
		
		return model;
	}

	@Override
	public CreateModelResponse add(CreateModelRequest createRequest) {
		checkIfBrandExistById(createRequest.getBrandId());
		checkIfModelExistsByName(createRequest.getName());
		Model model = modelMapperService.forRequest().map(createRequest, Model.class);
		model.setId(UUID.randomUUID().toString());
		modelRepository.save(model);
		
		CreateModelResponse response = modelMapperService.forResponse().map(model, CreateModelResponse.class);
		Brand brand = brandService.getBrandNameByBrandId(model.getBrand().getId());
		response.setBrandName(brand.getName());
		return response;
	}

	@Override
	public UpdateModelResponse update(UpdateModelRequest updateRequest) {
		checkIfModelExistsById(updateRequest.getId());
		checkIfBrandExistById(updateRequest.getBrandId());
		checkIfModelExistsByName(updateRequest.getName());
		
		Model model = modelMapperService.forRequest().map(updateRequest, Model.class);
		modelRepository.save(model);
		
		UpdateModelResponse response = modelMapperService.forResponse().map(model, UpdateModelResponse.class);
		Brand brand = brandService.getBrandNameByBrandId(model.getBrand().getId());
		response.setBrandName(brand.getName());
		return response;
	}

	@Override
	public void delete(String id) {
		checkIfModelExistsById(id);
		modelRepository.deleteById(id);
	}
	
	/// Private Rules \\\
	
	private void checkIfBrandExistById(String id) {
		brandService.getBrandById(id);
	}
	
	private void checkIfModelExistsById(String id) {
		if(modelRepository.findById(id) == null) {
			throw new BusinessException(MessagesForModel.ModelNotExist);
		}
	}
	
	private void checkIfModelExistsByName(String name) {
		brandService.getBrandByName(name);
	}
	
}
