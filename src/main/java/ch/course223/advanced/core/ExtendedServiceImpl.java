package ch.course223.advanced.core;

import ch.course223.advanced.error.BadRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public abstract class ExtendedServiceImpl<T extends ExtendedEntity> implements ExtendedService<T> {

	protected ExtendedJpaRepository<T> repository;


	public ExtendedServiceImpl(ExtendedJpaRepository<T> repository) {
		this.repository = repository;
	}

	@Override
	public T save(T entity) {
		return repository.save(entity);
	}

	@Override
	public Void deleteById(String id) throws NoSuchElementException {
		if(repository.existsById(id)) {
			repository.deleteById(id);
		} else {
			throw new NoSuchElementException(String.format("Entity with ID '%s' could not be found", id));
		}
		return null;
	}

	@Override
	public T updateById(String id, T entity) throws NoSuchElementException, BadRequestException {
		if(repository.existsById(id)) {
			checkUpdatedEntityId(id, entity);

			entity.setId(id);
			return repository.save(entity);
		} else {
			throw new NoSuchElementException(String.format("Entity with ID '%s' could not be found", id));
		}
	}

	@Override
	public List<T> findAll() {
		return repository.findAll();
	}

	@Override
	public T findById(String id) {
		return findOrThrow(repository.findById(id));
	}

	@Override
	public boolean existsById(String id) {
		return repository.existsById(id);
	}

	protected T findOrThrow(Optional<T> optional) throws NoSuchElementException {
		if(optional.isPresent()) {
			return optional.get();
		} else {
			throw new NoSuchElementException("No value present");
		}
	}

	protected void checkUpdatedEntityId(String id, T entity) throws BadRequestException {

		if(entity.getId() != null) {
			if(!id.equals(entity.getId())) {
				throw new BadRequestException(String.format("Path variable ID '%s' and Request body ID '%s' are not equal", id, entity.getId()));
			}
		}
	}

}
