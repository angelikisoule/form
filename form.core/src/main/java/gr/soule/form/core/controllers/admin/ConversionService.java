package gr.soule.form.core.controllers.admin;

import gr.media24.mSites.core.service.CategoryService;
import gr.media24.mSites.core.service.PublicationService;
import gr.media24.mSites.core.service.RoleService;
import gr.media24.mSites.data.entities.Category;
import gr.media24.mSites.data.entities.Publication;
import gr.media24.mSites.data.entities.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Service;

/**
 * Application's Conversion Service
 * @author npapadopoulos
 */
@Service
public class ConversionService extends FormattingConversionServiceFactoryBean {

	@Autowired private RoleService roleService;
	@Autowired private CategoryService categoryService;
	@Autowired private PublicationService publicationService;
	
    @SuppressWarnings("deprecation")
	@Override
    protected void installFormatters(FormatterRegistry registry) {
        super.installFormatters(registry);
    }
    
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installConverters(getObject()); //Register Application Converters
    }
    
    public void installConverters(FormatterRegistry registry) {
    	registry.addConverter(getIdToRoleConverter());
    	registry.addConverter(getStringToRoleConverter());
    	registry.addConverter(getIdToCategoryConverter());
    	registry.addConverter(getStringToCategoryConverter());
    	registry.addConverter(getIdToPublicationConverter());
    	registry.addConverter(getStringToPublicationConverter());	
    }
    
    public Converter<Long, Role> getIdToRoleConverter() {
        return new Converter<Long, Role>() {
            public Role convert(Long id) {
                return roleService.getRole(id);
            }
        };
    }
	
    public Converter<String, Role> getStringToRoleConverter() {
        return new Converter<String, Role>() {
            public Role convert(String id) {
            	if(id.equals("")) return null;
            		else return getObject().convert(Long.valueOf(id), Role.class);
            }
        };
    }
    
    public Converter<Long, Category> getIdToCategoryConverter() {
        return new Converter<Long, Category>() {
            public Category convert(Long id) {
                return categoryService.getCategory(id);
            }
        };
    }
	
    public Converter<String, Category> getStringToCategoryConverter() {
        return new Converter<String, Category>() {
            public Category convert(String id) {
            	if(id.equals("")) return null;
            		else return getObject().convert(Long.valueOf(id), Category.class);
            }
        };
    }
    
    public Converter<Long, Publication> getIdToPublicationConverter() {
        return new Converter<Long, Publication>() {
            public Publication convert(Long id) {
                return publicationService.getPublication(id);
            }
        };
    }
	
    public Converter<String, Publication> getStringToPublicationConverter() {
        return new Converter<String, Publication>() {
            public Publication convert(String id) {
            	if(id.equals("")) return null;
            		else return getObject().convert(Long.valueOf(id), Publication.class);
            }
        };
    }
}
