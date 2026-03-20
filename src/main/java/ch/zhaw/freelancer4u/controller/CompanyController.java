package ch.zhaw.freelancer4u.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.zhaw.freelancer4u.model.Company;
import ch.zhaw.freelancer4u.model.CompanyCreateDTO;
import ch.zhaw.freelancer4u.repository.CompanyRepository;

@RestController
@RequestMapping("/api")
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @PostMapping("/company")
    public ResponseEntity<Company> createCompany(
        @RequestBody CompanyCreateDTO fDTO) {
        Company fDAO = new Company(fDTO.getName(), fDTO.getEmail());
        Company f = companyRepository.save(fDAO);
        return new ResponseEntity<>(f, HttpStatus.CREATED);
    }

    @GetMapping("/company")
    public ResponseEntity<List<Company>> getAllFreelancer() {
        List<Company> allCompanies = companyRepository.findAll();
        return new ResponseEntity<>(allCompanies, HttpStatus.OK);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable String id) {
        Optional<Company> optCompany = companyRepository.findById(id);
        if (optCompany.isPresent()) {
            return new ResponseEntity<>(optCompany.get(), HttpStatus.OK);
        } else { 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
