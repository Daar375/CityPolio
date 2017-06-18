/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphGist;

/**
 *
 * @author Malab
 */
public interface CypherExportable {
    
    public String getNodeName();
    public String getNodeType();
    public String getNodeProperties();
    public String getNodeRelation();
}
