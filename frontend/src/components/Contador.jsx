import { useState } from "react";

const Contador = () => {
  const [contador, setContador] = useState(0);

  const updateContador = () => {
    setContador(contador + 1);
  };

  return (
    <div id="contador">
      <div>{contador}</div>
      <button onClick={updateContador}>Click</button>
    </div>
  );
};

export default Contador;
