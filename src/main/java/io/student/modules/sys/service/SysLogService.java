/**
 * 
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.student.modules.sys.service;


import com.baomidou.mybatisplus.service.IService;
import io.student.common.utils.PageUtils;
import io.student.modules.sys.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 * 
 * @author duanxin
 * @email sxz147@163.com
 * @date 2017-03-08 10:40:56
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
