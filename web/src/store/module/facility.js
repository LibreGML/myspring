const facility = {
  state: {
    screenWidth: document.body.clientWidth,
  },
  mutations: {
    setScreenWidth(state, width) {
      state.screenWidth = width;
    },
  },
};

export default facility;
