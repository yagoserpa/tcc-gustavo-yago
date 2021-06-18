import { useEffect, useState } from "react";

function Planetas({ title }) {
  const [{ loading, planets, search }, setState] = useState({
    loading: true,
    search: "",
  });

  useEffect(() => {
    fetch("https://swapi.dev/api/planets/")
      .then((r) => r.json())
      .then((r) => {
        setState((prev) => ({ ...prev, loading: false, planets: r.results }));
      });
  });

  return (
    <div>
      <h1>{title}</h1>
      <input
        className="fancy-form"
        value={search}
        onChange={(e) =>
          setState((prev) => ({ ...prev, search: e.target.value }))
        }
      />
      {loading ? (
        "Carregando..."
      ) : (
        <table>
          <thead>
            <tr>
              <th>Nome</th>
              <th>Clima</th>
            </tr>
          </thead>
          <tbody>
            {planets
              .filter(
                (i) =>
                  i.name.toLowerCase().includes(search) ||
                  i.climate.toLowerCase().includes(search)
              )
              .map((planet) => {
                return (
                  <tr>
                    <td>{planet.name}</td>
                    <td>{planet.climate}</td>
                  </tr>
                );
              })}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default Planetas;
