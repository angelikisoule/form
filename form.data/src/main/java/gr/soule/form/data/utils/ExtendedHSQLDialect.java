package gr.soule.form.data.utils;

import java.sql.Types;

import org.hibernate.dialect.HSQLDialect;

/**
 * Extend HSQLDialect Which Is Used For Unit Testing
 * @author npapadopoulos
 */
public class ExtendedHSQLDialect extends HSQLDialect {

	public ExtendedHSQLDialect() {
		super();
		/**
		 * The Default registerColumnType(Types.CLOB, "clob($l)") If Does Not Find A Column Length Attribute 
		 * Set, Uses The Default Value Of 255. In Such Case HSQLDialect Creates Column As CLOB(255) Which Is
		 * Funny For CLOBS. We Override The Column Definition So That We Do Not Have To Define Length Attributes 
		 */
		registerColumnType(Types.CLOB, "clob");
	}
	
	@Override
	public boolean dropConstraints() {
		return false;
	}

	@Override
	public boolean supportsIfExistsBeforeTableName() {
		return true;
	}

	@Override
	public boolean supportsIfExistsAfterTableName() {
		return false;
	}

	@Override
	public String getCascadeConstraintsString() {
		return " CASCADE ";
	}
}
