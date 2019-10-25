import java.lang.reflect.*;

public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
    	
    	//Recursion break
    	if(c.getSuperclass() == null || c.getInterfaces() == null) {
    		return;
    	}
    	
    	// Get name of declaring class
    	String className = c.getName();
    	System.out.println();
    	printSpacing(depth);
    	System.out.println("Declaring Class Name: " + className);
    	
    	// Get super-class name
    	Class classSuper = c.getSuperclass();
    	printSpacing(depth);
    	System.out.println("Super Class Name: " + classSuper.getName());
    	inspectClass(classSuper, obj, recursive, depth+1);
    	
    	
    	// Get interfaces
    	Class[] interfaces = c.getInterfaces();
    	for(Class classInterfaces : interfaces) {
    		printSpacing(depth);
    		System.out.println("Interface Name: " + classInterfaces.getName());
    		inspectClass(classInterfaces, obj, recursive, depth+1);
    	}
    	
    	// Get and print constructors
    	Constructor[] classConstructors = c.getConstructors();
    	for(Constructor classConstructor: classConstructors) {
    		printConstructor(classConstructor, depth);
    	}
    	// Get and print methods
    	Method[] classMethods = c.getMethods();
    	String methodName;
    	for(Method classMethod: classMethods) {
    		//Print Method Name
    		methodName = classMethod.getName();
    		System.out.println();
    		printSpacing(depth);
    		System.out.println(className + " Method Name: " + methodName);
    		
    		//Print exceptions
    		Class[] methodExceptions = classMethod.getExceptionTypes();
    		for(Class methodExeption: methodExceptions) {
    			printSpacing(depth);
    			System.out.println("Exception: " + methodExeption.getName());
    		}
    		
    		//Print Parameter Types
    		Class[] methodParameters = classMethod.getParameterTypes();
    		for(Class methodParameter: methodParameters) {
    			printSpacing(depth);
    			System.out.println("Parameter Type: " + methodParameter.getName());
    		}
    		
    		//Print Return Type
    		printSpacing(depth);
    		System.out.println("Return Type: " + classMethod.getReturnType().getTypeName());
    	}
    	
    	System.out.println();
    	
    	// Get and print fields
    	String classFieldName;
    	Field[] classFields = c.getDeclaredFields();
    	for(Field classField: classFields) {
    		classFieldName = classField.getName();
    		classField.setAccessible(true);
    		printSpacing(depth);
    		System.out.println("Field Name: " + classFieldName);
    		printSpacing(depth);
    		System.out.println(classFieldName + " Field Type: " + classField.getType().getTypeName());
    		printSpacing(depth);
    		System.out.println(classFieldName + " Modifier: " + Modifier.toString(classField.getModifiers()));
    		
    		try {
				Object value = classField.get(obj);
				printSpacing(depth);
	    		System.out.println(className + " Field Value: " + value);
	    		if(recursive = true) {
	    			inspectClass(value.getClass(), value, recursive, depth+1);
	    		}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		System.out.println();
    		

    	}
    	
    }
    
    private void printSpacing(int depth) {
    	for(int i = 0; i < depth*4; i++) {
    		System.out.print(" ");
    	}
    }
    
    private void printConstructor(Constructor classConstructor, int depth) {
    	System.out.println();
		printSpacing(depth);
		System.out.println("Constructor Name: " + classConstructor.getName());
		Class[] parameterTypes = classConstructor.getParameterTypes();
		
		printSpacing(depth);
		System.out.print(classConstructor.getName() + " Parameter Type: ");
		for(Class parameterType: parameterTypes) {
			printSpacing(depth);
			System.out.print(parameterType.getName());
		}
		System.out.println();
		
		printSpacing(depth);
		System.out.println(classConstructor.getName() + " Modifier: " + Modifier.toString(classConstructor.getModifiers()));
    }
}