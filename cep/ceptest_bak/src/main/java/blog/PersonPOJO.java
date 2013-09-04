package blog;

import java.util.List;
import java.util.Map;

public class PersonPOJO
{
	String name;
	int age;
	List<Child> children;
	Map<String, Integer> phones;
	Address address;

	public String getName()
	{
		return name;
	}

	public int getAge()
	{
		return age;
	}

	public Child getChildren(int index)
	{
		return children.get(index);
	}
	
	// 此方法用于phones属性的更新
	public void setPhones(String name, Integer number){
		phones.put(name, number);
	}

	public int getPhones(String name)
	{
		return phones.get(name);
	}

	public Address getAddress()
	{
		return address;
	}
	// Address，Child不变
}

class Child
{
	String name;
	int gender;
	// 省略getter方法
}

class Address
{
	String road;
	String street;
	int houseNo;
	// 省略getter方法
}

