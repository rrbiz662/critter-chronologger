package com.udacity.jdnd.course3.critter.pet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jdnd.course3.critter.user.Customer;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
	@Autowired
	private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.savePet(mapDtoToPet(petDTO));
        
    	return mapPetToDto(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return mapPetToDto(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> petDTOs = new ArrayList<PetDTO>();
        
    	for (Pet pet: petService.getPets()) {
    		petDTOs.add(mapPetToDto(pet));
		}
        
    	return petDTOs;        
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
    	List<PetDTO> petDTOs = new ArrayList<PetDTO>();
        
    	for (Pet pet: petService.getPetsByOwner(ownerId)) {
    		petDTOs.add(mapPetToDto(pet));
		}
    	
    	return petDTOs;
    }
    
    @PutMapping("/{petId}")
    public PetDTO updatePet(@PathVariable long petId, @RequestBody PetDTO petDTO) {
    	Pet pet = petService.getPet(petId);
    	if(pet != null) {
    		
    	  	Customer customer = new Customer();
        	//customer.setId(petDTO.getOwnerId());
    		
    		pet.setType(petDTO.getType());
    		pet.setName(petDTO.getName());
    		pet.setBirthDate(petDTO.getBirthDate());
    		pet.setNotes(petDTO.getNotes());
    		//pet.setCustomer(customer);
    		
    		Pet updatedPet = petService.savePet(pet);
    		
    		return mapPetToDto(updatedPet);
    	}
    	
    	return null; 
    }
    
    @DeleteMapping("/{petId}")
    public void deletePet(@PathVariable long petId) {
    	petService.deletePet(petId);
    }
    
    private Pet mapDtoToPet(PetDTO petDTO) {
    	Pet pet = new Pet();
    	Customer customer = new Customer();
    	//customer.setId(petDTO.getOwnerId());
    	
    	pet.setId(petDTO.getId());
    	pet.setType(petDTO.getType());
    	pet.setName(petDTO.getName());
    	pet.setBirthDate(petDTO.getBirthDate());
    	pet.setNotes(petDTO.getNotes());
    	//pet.setCustomer(customer);
    	
    	return pet;
    }
    
    private PetDTO mapPetToDto(Pet pet) {
    	PetDTO petDTO = new PetDTO();
    	
    	petDTO.setId(pet.getId());
    	petDTO.setType(pet.getType());
    	petDTO.setName(pet.getName());
    	//petDTO.setOwnerId(pet.getCustomer().getId());
    	petDTO.setBirthDate(pet.getBirthDate());
    	petDTO.setNotes(pet.getNotes());    	
    	
    	return petDTO;
    }
}
