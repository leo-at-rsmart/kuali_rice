package org.kuali.rice.krms.api.repository;

import java.util.List;

public interface ActionContract {
	/**
	 * This is the ID for the Action
	 *
	 * <p>
	 * It is a ID of a Action
	 * </p>
	 * @return ID for Action
	 */
	public String getActionId();

	/**
	 * This is the name of the Action 
	 *
	 * <p>
	 * name - the name of the Action
	 * </p>
	 * @return the name of the Action
	 */
	public String getName();

	/**
	 * This is the namespace of the Action 
	 *
	 * <p>
	 * The namespace of the Action
	 * </p>
	 * @return the namespace of the Action
	 */
	public String getNamespace();

    /**
     * This is the description for what the parameter is used for.  This can be null or a blank string.
     * @return description
     */
	public String getDescription();

	/**
	 * This is the KrmsType of the Action
	 *
	 * @return id for KRMS type related of the Action
	 */
	public String getTypeId();
	/**
	 * This method returns a list of attributes associated with the 
	 * Action
	 * 
	 * @return a list of ActionAttribute objects.
	 */
	public List<? extends ActionAttributeContract> getAttributes();
	

}
