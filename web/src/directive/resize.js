const elementResizeDetectorMaker = require("element-resize-detector");
const erdUltraFast = elementResizeDetectorMaker({
  strategy: "scroll", //<- For ultra performance.
});

const context = "@@resizeContext";

const handleEvent = function(el, binding) {
  const handle = function(element) {
    const width = element.offsetWidth;
    const height = element.offsetHeight;
    binding.value && binding.value({ width, height });
  };

  if (!el[context]) {
    el[context] = {
      removeHandle: handle,
    };
  } else {
    el[context].removeHandle = handle;
  }

  return handle;
};

export default {
  bind(el, binding) {
    erdUltraFast.listenTo(el, handleEvent(el, binding));
  },

  update(el, binding) {
    erdUltraFast.removeListener(el, el[context].removeHandle);
    erdUltraFast.listenTo(el, handleEvent(el, binding));
  },

  unbind(el) {
    erdUltraFast.removeListener(el, el[context].removeHandle);
  },
};
