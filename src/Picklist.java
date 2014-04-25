import javax.swing.JTextField;


public class Picklist {

	public String formName;
	public String formNamePlural;
	public String formNameSpaces;
	public String formNamePluralSpaces;
	
	public Picklist(String formName, String formNamePlural, String formNameSpaces, String formNamePluralSpaces)
	{
		this.formName = formName;
		this.formNamePlural = formNamePlural;
		this.formNameSpaces = formNameSpaces;
		this.formNamePluralSpaces = formNamePluralSpaces;
	}
	
	private void createForm(String projectDirectory)
	{
		
	}
	private void createView(String projectDirectory)
	{
		
	}
	private void updateViews(String projectDirectory)
	{
		//xpSupAll
		FileUtils.insertIntoFileAfter(projectDirectory +"/Views/($xpSupAll).view" , "SELECT", " Form = \""+ formName + "\" |");
		FileUtils.insertIntoFileAfter(projectDirectory +"/Views/($xpSupAll).view" , "Form = \"User\" ; \"Users\";", "Form = \""+ formName+ "\" ; \""+ formNamePluralSpaces +"\";");
		
		//xpSupAllHome
		FileUtils.insertIntoFileAfter(projectDirectory +"/Views/($xpSupAllHome).view" , "SELECT", " Form = \""+ formName + "\" |");
		FileUtils.insertIntoFileAfter(projectDirectory +"/Views/($xpSupAllHome).view" , "Form = \"User\" ; \"Users\";", "Form = \""+ formName+ "\" ; \""+ formNamePluralSpaces +"\";");
		
		//xpSupAllCompanyData
		FileUtils.insertIntoFileAfter(projectDirectory +"/Views/($xpSupAllCompanyData).view" , "SELECT", " Form = \""+ formName + "\" |");
		FileUtils.insertIntoFileAfter(projectDirectory +"/Views/($xpSupAllCompanyData).view" , "Form = \"User\" ; \"Users\";", "Form = \""+ formName+ "\" ; \""+ formNamePluralSpaces +"\";");
		
	}
	private void updateSettings(String projectDirectory)
	{
		String data = "\n{\"Filter\": \"" + formNamePluralSpaces +"\", \"label\": \""+ formNamePluralSpaces +"\", \"form\": \"./Setting.xsp?form="+ formName + "\", \"newButtonLabel\": \"New " + formNameSpaces + "\", \"rootlink\": \"./Settings.xsp\"  }\n";
		FileUtils.insertIntoFileAfter(projectDirectory +"/Code/xpSettings.jss" , "var SETTINGS_COMPANY_DATA_SECOND_LEVEL = [", data);
	}
	public void create(String projectDirectory)
	{
		createForm(projectDirectory);
		createView(projectDirectory);
		updateViews(projectDirectory);
		updateSettings(projectDirectory);
	}
	public String toString()
	{
		return formNamePlural + "~" + formName;
	}
}
