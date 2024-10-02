export const allUrlParam = function() {
  const params = new URLSearchParams(location.search);
  const iterator = params.entries();
  const theRequest = {};
  for (const e of iterator) {
    theRequest[e[0]] = e[1];
  }
  return theRequest;
};

export default {
  allUrlParam,
};
