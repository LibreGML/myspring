import localStorage from "@/utils/localStorage";

const key = "lock-password";
const lock = {
  state: {
    isLock: !!localStorage.getItem(key),
  },
  mutations: {
    updateLock(state, password) {
      if (password) {
        localStorage.setItem(key, password);
        state.isLock = true;
      } else {
        localStorage.removeItem(key);
        state.isLock = false;
      }
    },
  },
};

export default lock;
