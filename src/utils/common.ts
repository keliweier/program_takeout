export const checkProcessEnv =() => {
  return process.env.VUE_APP_DELETE_PERMISSIONS==='true'
}
export const debounce = (fn: (...args: any[]) => any, time?: number) => {
  time = time || 200
  // 定时器
  let timer: ReturnType<typeof setTimeout> | null = null
  return function(this: unknown, ...args: any[]) {
    const _this = this
    if (timer) {
      clearTimeout(timer)
    }
    timer = setTimeout(function() {
      timer = null
      fn.apply(_this, args)
    }, time)
  }
  
};
//节流
export const throttle = (fn: (...args: any[]) => any, time?: number) => {
  let timer: ReturnType<typeof setTimeout> | null = null
  time = time || 1000
  return function(this: unknown, ...args: any[]) {
    if (timer) {
      return
    }
    const _this = this
    timer = setTimeout(() => {
      timer = null
    }, time)
    fn.apply(_this, args)
  }
}
// 判断正、负
export const strIncrease = (str: string) => {
  if(str.slice(0,1) ==='-'){
    return true
    }
}
