import axios from 'axios'

/**
 * 用户登录
 * @param {string} username - 用户名
 * @param {string} password - 密码
 * @returns {Promise} - 返回登录结果
 */
export const login = async (username, password) => {
  const url = `/user/login?username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`;
  console.log('请求地址:', url);
  try {
    const response = await axios.post(url);
    const {statusCode, statusCodeValue, body} = response.data;
    console.log('token:', body);
    return response.data;
  } catch (error) {
    console.error('登录接口调用失败', error);
    throw error;
  }
};

/**
 * 用户注册
 * @param {Object} data - 注册数据
 * @param {string} data.userName - 用户名
 * @param {string} data.userPwd - 密码
 * @param {number} data.userType - 用户类型（默认0为普通用户）
 * @returns {Promise} - 返回注册结果
 */
export const register = async (data) => {
  try {
    const response = await axios.post('/user/add', {
      userId: '', // 后端自动注入
      userName: data.userName,
      userPwd: data.userPwd,
      userType: 0, // 默认普通用户
    });
    return response.data;
  } catch (error) {
    console.error('注册接口调用失败:', error);
    throw error;
  }
}; 