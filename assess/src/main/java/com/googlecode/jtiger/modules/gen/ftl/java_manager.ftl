package ${packageName ? default('')}.${className?lower_case}.service;

import org.springframework.stereotype.Service;

import com.googlecode.jtiger.core.service.BaseGenericsManager;
import ${packageName ? default('')}.${className?lower_case}.model.${className ? default('')};

/**
 * Service Class for ${tableName ? default('')}
 */
@Service
public class ${className ? default('')}Manager extends BaseGenericsManager<${className ? default('')}> {

}