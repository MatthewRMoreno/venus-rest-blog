/**
 * @param state
 * @param request
 * @returns {Promise<{}>}
 */
export default function fetchData(state, request) {
    const promises = [];
    const baseUri = "http://localhost:8081";

    console.log("got to fetch data");
    console.log(request);
    for (let pieceOfState of Object.keys(state)) {
        console.log(baseUri + state[pieceOfState]);
        promises.push(
            fetch(baseUri + state[pieceOfState], request)
                .then(function (res) {
                    return res.json();
                }));
    }
    return Promise.all(promises).then(propsData => {
        const props = {};
        Object.keys(state).forEach((key, index) => {
            props[key] = propsData[index];
        });
        return props;
    });
}