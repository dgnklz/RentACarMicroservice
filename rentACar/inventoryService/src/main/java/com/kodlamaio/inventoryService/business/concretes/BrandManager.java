package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryService.business.abstracts.BrandService;
import com.kodlamaio.inventoryService.business.constants.MessagesForBrand;
import com.kodlamaio.inventoryService.business.requests.create.CreateBrandRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateBrandResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllBrandsResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetBrandResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateBrandResponse;
import com.kodlamaio.inventoryService.dataAccess.BrandRepository;
import com.kodlamaio.inventoryService.entities.Brand;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {
	private BrandRepository brandRepository;
	private ModelMapperService modelMapperService;

	@Override
	public List<GetAllBrandsResponse> getAll() {
		List<Brand> brands = brandRepository.findAll();
		List<GetAllBrandsResponse> responses = brands.stream().map(brand -> modelMapperService.forResponse().map(brand, GetAllBrandsResponse.class))
				.toList();
		return responses;
	}

	@Override
	public GetBrandResponse getBrandById(String id) {
		checkIfBrandExistsById(id);
		
		Brand brand = brandRepository.findById(id).get();
		
		GetBrandResponse response = modelMapperService.forResponse().map(brand, GetBrandResponse.class);
		return response;
	}

	@Override
	public GetBrandResponse getBrandByName(String name) {
		checkIfBrandExistsByName(name);
		
		Brand brand = brandRepository.findByName(name);
		
		GetBrandResponse response = modelMapperService.forResponse().map(brand, GetBrandResponse.class);
		return response;
	}
	
	public Brand getBrandNameByBrandId(String id) {
		checkIfBrandExistsById(id);
		
		Brand brand = brandRepository.findById(id).orElse(null);
		
		return brand;
	}

	@Override
	public CreateBrandResponse add(CreateBrandRequest createRequest) {
		Brand brand = modelMapperService.forRequest().map(createRequest, Brand.class);
		brand.setId(UUID.randomUUID().toString());
		brandRepository.save(brand);
		
		CreateBrandResponse response = modelMapperService.forResponse().map(brand, CreateBrandResponse.class);
		return response;
	}

	@Override
	public UpdateBrandResponse update(UpdateBrandRequest updateRequest) {
		checkIfBrandExistsById(updateRequest.getId());
		
		Brand brand = modelMapperService.forRequest().map(updateRequest, Brand.class);
		brandRepository.save(brand);
		
		UpdateBrandResponse response = modelMapperService.forResponse().map(brand, UpdateBrandResponse.class);
		return response;
	}

	@Override
	public void delete(String id) {
		checkIfBrandExistsById(id);
		brandRepository.deleteById(id);
	}

	/// Public Rules \\\

	/// Private Rules \\\

	private void checkIfBrandExistsById(String id) {
		if (brandRepository.findById(id) == null) {
			throw new BusinessException(MessagesForBrand.BrandNotExist);
		}
	}

	private void checkIfBrandExistsByName(String name) {
		if (brandRepository.findByName(name) == null) {
			throw new BusinessException(MessagesForBrand.BrandNotExist);
		}
	}

}
