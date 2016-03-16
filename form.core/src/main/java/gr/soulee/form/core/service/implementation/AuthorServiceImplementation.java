package gr.media24.mSites.core.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import gr.media24.mSites.core.service.AuthorService;
import gr.media24.mSites.data.dao.AuthorDao;
import gr.media24.mSites.data.entities.Author;

@Service
@Transactional(readOnly = true)
public class AuthorServiceImplementation implements AuthorService {

	@Autowired private AuthorDao authorDao;
	
	@Override
	@Transactional(readOnly = false)
	public void saveAuthor(Author author, Errors errors) {
		validateForm(author, errors);
		if(!errors.hasErrors()) authorDao.persistOrMerge(author);
	}
	
	@Override
	public Author getAuthor(Long id) {
		return authorDao.get(id);
	}
	
	@Override
	public List<Author> getAuthors() {
		return (List<Author>) authorDao.getAll();
	}
	
	@Override
	public List<Author> getAuthors(int maxAuthors) {
		return authorDao.getAll(maxAuthors);
	}

	@Override
	public List<Author> getAuthors(int maxAuthors, int offset) {
		return authorDao.getAll(maxAuthors, offset);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteAuthor(Long id) {
		authorDao.deleteById(id);
	}

	@Override
	public Long countAuthors() {
		return authorDao.count();
	}
	
	/**
	 * Custom Validations For Form Fields
	 * @param author Author Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateForm(Author author, Errors errors) {
		validateName(author, errors);
	}
	
	/**
	 * Validate Uniqueness Of Author's Name
	 * @param author Author Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateName(Author author, Errors errors) {
		Author existing = authorDao.getByName(author.getName());
		if(existing!=null && !existing.getId().equals(author.getId())) { //Duplicate Name
			errors.rejectValue("name", "error.duplicate");
		}
	}
}