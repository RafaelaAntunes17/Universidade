const canvas1 = document.getElementById("canvas1");
const canvas2 = document.getElementById("canvas2");
const canvas3 = document.getElementById("canvas3");
const ctx1 = canvas1.getContext("2d");
const ctx2 = canvas2.getContext("2d");
const ctx3 = canvas3.getContext("2d");
const radiusSlider = document.getElementById("radiusSlider");
const radiusSlider2 = document.getElementById("radiusSlider2");
const centerXInput = document.getElementById("centerX");
const centerYInput = document.getElementById("centerY");
const fluxoCheckbox = document.getElementById("fluxo");

let radius = 80;
let dragging = false;

let offsetX2 = 200; // centro do canvas2
let offsetY2 = 200;
let scale2 = 1;
let offsetX1 = 200;
let offsetY1 = 200;
let scale1 = 1;
let offsetX3 = 200;
let offsetY3 = 200;
let scale3 = 1;
let fluxoAtivo = false;


// Estado do centro em coordenadas reais
let centerReal = { x: 0, y: 0 };

const radiusValue = document.getElementById("radiusValue");
const radiusValue2 = document.getElementById("radiusValue2");

radiusSlider.addEventListener("input", (e) => {
  radius = parseFloat(e.target.value);
  radiusValue.textContent = (radius / 80).toFixed(2);
  drawCircle();
  drawTransformed();
});

radiusSlider2.addEventListener("input", (e) => {
  let r = parseFloat(e.target.value);
  radiusValue2.textContent = (r / 80).toFixed(2);
  elipses(r);
});

centerXInput.addEventListener("input", (e) => {
  centerReal.x = parseFloat(e.target.value);
  drawCircle();
  drawTransformed();
});

centerYInput.addEventListener("input", (e) => {
  centerReal.y = parseFloat(e.target.value);
  drawCircle();
  drawTransformed();
});

function elipses(r) {
  ctx3.clearRect(0, 0, canvas3.width, canvas3.height);
  drawAxes(ctx3, { x: "x", y: "y" }, offsetX3, offsetY3, scale3);

  ctx3.beginPath();
  ctx3.arc(offsetX3, offsetY3, r * scale1, 0, 2 * Math.PI);
  ctx3.strokeStyle = "black";
  ctx3.lineWidth = 2;
  ctx3.stroke();

  let points = [];

  for (let i = 0; i <= 2 * Math.PI; i += 0.05) {
    const x = (r / 80) * Math.cos(i);
    const y = (r / 80) * Math.sin(i);

    const { u, v } = applyTransformation(x, y, "joukowsky");

    const tx = offsetX3 + (u * -1) * 80 * scale3;
    const ty = offsetY3 - v * 80 * scale3;

    points.push({ x: tx, y: ty });
  }

  ctx3.beginPath();
  ctx3.moveTo(points[0].x, points[0].y);
  for (let i = 1; i < points.length; i++) {
    ctx3.lineTo(points[i].x, points[i].y);
  }
  ctx3.closePath();
  ctx3.strokeStyle = "red";
  ctx3.lineWidth = 3;
  ctx3.stroke();


  [{ x: -2, y: 0 }, { x: 2, y: 0 }].forEach(foco => {
    ctx3.beginPath();
    ctx3.arc(offsetX3 + foco.x * 80 * scale3,
      offsetY3 + foco.y * 80 * scale3,
      3, 0, 2 * Math.PI
    );

    ctx3.fillStyle = "blue";
    ctx3.fill();
  })

}

function drawAxes(ctx, axisLabels = { x: "x", y: "y" }, offsetX = 200, offsetY = 200, scale = 1) {
  const w = ctx.canvas.width;
  const h = ctx.canvas.height;

  ctx.clearRect(0, 0, w, h);

  const step = 1; // unidades reais
  const pixelStep = step * 80 * scale;

  ctx.strokeStyle = "#ccc";
  ctx.lineWidth = 1;
  ctx.beginPath();

  // Eixo X
  ctx.moveTo(0, offsetY);
  ctx.lineTo(w, offsetY);
  // Eixo Y
  ctx.moveTo(offsetX, 0);
  ctx.lineTo(offsetX, h);
  ctx.stroke();

  // Labels dos eixos
  ctx.fillStyle = "#000";
  ctx.font = "bold 14px Segoe UI";
  ctx.textAlign = "right";
  ctx.fillText(axisLabels.x, w - 5, offsetY - 6);
  ctx.textAlign = "left";
  ctx.fillText(axisLabels.y, offsetX + 6, 10);

  // Marcas horizontais (eixo x)
  ctx.strokeStyle = "#ccc";
  ctx.fillStyle = "#555";
  ctx.font = "12px Arial";
  ctx.textAlign = "center";
  ctx.textBaseline = "top";

  const realStartX = -(offsetX / (80 * scale));
  const realEndX = (w - offsetX) / (80 * scale);

  for (let x = Math.floor(realStartX); x < realEndX; x += step) {
    const px = offsetX + x * 80 * scale;
    ctx.beginPath();
    ctx.moveTo(px, offsetY - 5);
    ctx.lineTo(px, offsetY + 5);
    ctx.stroke();
    ctx.fillText(x.toFixed(1), px, offsetY + 8);
  }

  // Marcas verticais (eixo y)
  ctx.textAlign = "left";
  ctx.textBaseline = "middle";

  const realStartY = -(offsetY / (80 * scale));
  const realEndY = (h - offsetY) / (80 * scale);

  for (let y = Math.floor(realStartY); y < realEndY; y += step) {
    const py = offsetY - y * 80 * scale;
    ctx.beginPath();
    ctx.moveTo(offsetX - 5, py);
    ctx.lineTo(offsetX + 5, py);
    ctx.stroke();
    ctx.fillText(y.toFixed(1) + "i", offsetX + 6, py);
  }
}


function drawCircle() {
  ctx1.clearRect(0, 0, canvas1.width, canvas1.height);
  drawAxes(ctx1, { x: "x", y: "y" }, offsetX1, offsetY1, scale1);



  const cx = offsetX1 + centerReal.x * 80 * scale1;
  const cy = offsetY1 - centerReal.y * 80 * scale1;

  ctx1.beginPath();
  ctx1.arc(cx, cy, radius * scale1, 0, 2 * Math.PI);
  ctx1.strokeStyle = "blue";
  ctx1.lineWidth = 2;
  ctx1.stroke();

  ctx1.beginPath();
  ctx1.arc(cx, cy, 5, 0, 2 * Math.PI);
  ctx1.fillStyle = "black";
  ctx1.fill();

  drawLabels(ctx1);
}

function drawLabels(ctx) {
  ctx.fillStyle = "#000";
  ctx.font = "14px Segoe UI";

  ctx.fillText(`Centro: ${centerReal.x.toFixed(2)}, ${centerReal.y.toFixed(2)}`, 10, 20);
  ctx.fillText(`Raio: ${(radius / 80).toFixed(2)}`, 10, 40);
}


function applyTransformation(zx, zy, type) {
  if (type === "joukowsky") {
    const denom = zx ** 2 + zy ** 2;
    return {
      u: zx * (zx ** 2 + zy ** 2 + 1) / denom,
      v: zy * (zx ** 2 + zy ** 2 - 1) / denom
    };
  }
  return { u: zx, v: zy };
}
const width = canvas2.width;
const height = canvas2.height;
const scale = 50;
const centerX = width / 2;
const centerY = height / 2;

const U = 1.0;

function complexAdd(a, b) {
  return [a[0] + b[0], a[1] + b[1]];
}

function complexSub(a, b) {
  return [a[0] - b[0], a[1] - b[1]];
}

function complexMul(a, b) {
  return [
    a[0] * b[0] - a[1] * b[1],
    a[0] * b[1] + a[1] * b[0],
  ];
}

function complexDiv(a, b) {
  const denom = b[0] ** 2 + b[1] ** 2;
  return [
    (a[0] * b[0] + a[1] * b[1]) / denom,
    (a[1] * b[0] - a[0] * b[1]) / denom,
  ];
}

function complexAbs(z) {
  return Math.sqrt(z[0] ** 2 + z[1] ** 2);
}

function joukowski(z) {
  const inv = complexDiv([1, 0], z);
  return complexAdd(z, inv);
}

function streamFunction(z, R, Gamma) {
  const r2 = z[0] ** 2 + z[1] ** 2;
  const theta = Math.atan2(z[1], z[0]);
  return U * r2 * Math.sin(theta) - (Gamma / (2 * Math.PI)) * Math.log(r2);
}

function drawStreamlines() {
    const R = radius / 80;
    const h = centerReal.x;
    const k = centerReal.y;
    const alpha = Math.atan2(k, h + R);
    const U = 1;  // velocidade uniforme
    const Gamma = 4 * Math.PI * U * R * Math.sin(alpha);

    ctx2.clearRect(0, 0, canvas2.width, canvas2.height);
    drawAxes(ctx2, { x: "u", y: "v" }, offsetX2, offsetY2, scale2);
    


    ctx2.strokeStyle = "blue";
    ctx2.lineWidth = 1;

    // Valores fixos da função de corrente ψ para as linhas de corrente
    const numLines = 20;
    const psiMin = -3;
    const psiMax = 3;

    for (let i = 0; i < numLines; i++) {
        const psiVal = psiMin + (psiMax - psiMin) * i / (numLines - 1);
        let linePoints = [];

        // Para cada linha de corrente, percorremos um intervalo de x e calculamos y que satisfaça ψ(x,y)=psiVal
        // Aqui simplificamos e varremos x e buscamos y aproximado com ψ constante (resolvendo numericamente)

        for (let x = h - 3*R; x <= h + 5*R; x += 0.005) {
            // função para achar y tal que ψ(x,y)=psiVal (resolução simples por bissecção)
            let y = findYforPsi(x, psiVal, h, k, R, U, Gamma);
            if (y !== null) {
                // Verifica se ponto está fora do círculo (para evitar linhas dentro da figura)
                const dx = x - h;
                const dy = y - k;
                if (Math.sqrt(dx * dx + dy * dy) > R + 0.005) {
                    linePoints.push({ x, y });
                }
            }
        }

        // Transformar pontos pelo mapa de Joukowsky e desenhar
        if (linePoints.length > 1) {
            ctx2.beginPath();
            for (let j = 0; j < linePoints.length; j++) {
                const p = linePoints[j];
                const { u, v } = applyTransformation(p.x, p.y, "joukowsky");
                const tx = offsetX2 + (-u) * 80 * scale2;
                const ty = offsetY2 - v * 80 * scale2;

                if (j === 0) ctx2.moveTo(tx, ty);
                else ctx2.lineTo(tx, ty);
            }
            ctx2.stroke();
        }
    }
}

// Função que calcula a função de corrente ψ
function psi(x, y, h, k, R, U, Gamma) {
    const dx = x - h;
    const dy = y - k;
    const r = Math.sqrt(dx*dx + dy*dy);
    const theta = Math.atan2(dy, dx);
    if (r === 0) return 0;
    return U * r * Math.sin(theta) * (1 - (R*R)/(r*r)) + (Gamma / (2 * Math.PI)) * Math.log(r / R);
}

// Função que, para dado x e psiVal, encontra y aproximado tal que ψ(x,y) = psiVal (bissecção simples)
function findYforPsi(x, psiVal, h, k, R, U, Gamma) {
    // Intervalo de busca para y
    let yLow = k - 3*R;
    let yHigh = k + 3*R;
    let yMid;
    const tol = 1e-6; // Tolerância para convergência
    let maxIter = 30;

    for (let i = 0; i < maxIter; i++) {
        yMid = (yLow + yHigh) / 2;
        const val = psi(x, yMid, h, k, R, U, Gamma) - psiVal;
        if (Math.abs(val) < tol) return yMid;

        if (val > 0) yHigh = yMid;
        else yLow = yMid;
    }
    // Se não converge, retorna null
    return null;
}



function drawTransformed() {
  ctx2.clearRect(0, 0, canvas2.width, canvas2.height);
  drawAxes(ctx2, { x: "u", y: "v" }, offsetX2, offsetY2, scale2);
  drawCircle();
  if (fluxoAtivo) {
    drawStreamlines();
  }

  // <- Adicionado aqui

  const type = "joukowsky";
  const points = [];

  // Percorre a circunferência no plano z
  for (let t = 0; t < 2 * Math.PI; t += 0.01) {
    const zx = centerReal.x + (radius / 80) * Math.cos(t);
    const zy = centerReal.y + (radius / 80) * Math.sin(t);

    const { u, v } = applyTransformation(zx, zy, type);

    const tx = offsetX2 + (u * -1) * 80 * scale2;
    const ty = offsetY2 - v * 80 * scale2;

    points.push({ x: tx, y: ty });
  }

  ctx2.beginPath();
  ctx2.moveTo(points[0].x, points[0].y);
  for (let i = 1; i < points.length; i++) {
    ctx2.lineTo(points[i].x, points[i].y);
  }
  ctx2.closePath();
  ctx2.strokeStyle = "red";
  ctx2.lineWidth = 2;
  ctx2.stroke();
}







// Permitir arrastar o centro com o rato
canvas1.addEventListener("mousedown", (e) => {
  const rect = canvas1.getBoundingClientRect();
  const x = e.clientX - rect.left;
  const y = e.clientY - rect.top;
  const cx = 200 + centerReal.x * 80;
  const cy = 200 - centerReal.y * 80;
  const dx = x - cx;
  const dy = y - cy;
  if (Math.hypot(dx, dy) < 10) dragging = true;
});

canvas1.addEventListener("mousemove", (e) => {
  if (!dragging) return;
  const rect = canvas1.getBoundingClientRect();
  const x = e.clientX - rect.left;
  const y = e.clientY - rect.top;
  centerReal.x = (x - 200) / 80;
  centerReal.y = (200 - y) / 80;
  centerXInput.value = centerReal.x.toFixed(2);
  centerYInput.value = centerReal.y.toFixed(2);
  drawCircle();
  drawTransformed();

});



canvas1.addEventListener("mouseup", () => dragging = false);
canvas1.addEventListener("mouseleave", () => dragging = false);
fluxoCheckbox.addEventListener("change", () => {
  fluxoAtivo = fluxoCheckbox.checked;


  // Altera os limites do raio dinamicamente com base no estado do fluxo
  if (fluxoAtivo) {
    // Por exemplo: limites mais restritos
    radiusSlider.min = 80;
    radiusSlider.max = 150;

  } else {
    // Limites normais
    radiusSlider.min = 10;
    radiusSlider.max = 160;
  }

 
  drawTransformed();
});

// Inicialização
elipses(80);
radiusValue.textContent = (radius / 80).toFixed(2);
centerXInput.value = centerReal.x.toFixed(2);
centerYInput.value = centerReal.y.toFixed(2);
drawCircle();
drawTransformed();

const predefinidoBtn = document.getElementById("predefinido");

// Lista de valores predefinidos
const predefinidos = [
  { x: 0.1, y: 0, r: 0.5 },
  { x: 0.2, y: 1, r: 0.8 },
  { x: 1, y: 1, r: 1 },
  { x: -2, y: 3, r: 4.2426 },
  { x: 0.2, y: 1, r: 1.2806 },
  { x: 0.1, y: 0.3, r: 0.9487 },
  { x: -0.1, y: 0.1, r: 1.1045 },
  { x: -0.2, y: 0.1, r: 1.2042 },
  { x: 0.3, y: -0.2, r: 0.6000 },
  { x: -0.3, y: 0.5, r: 0.9000 },
  { x: -0.5, y: -0.5, r: 1.1000 },
  { x: 0.4, y: 0.6, r: 1.5000 },
  { x: 0.5, y: 0, r: 2.0000 },
  { x: -0.4, y: -0.3, r: 2.5000 },
  { x: 0.2, y: -0.4, r: 3.0000 },
  { x: -0.1, y: 0.2, r: 3.5000 },

];

let indiceAtual = 0;

predefinidoBtn.addEventListener("click", () => {
  const valor = predefinidos[indiceAtual];

  // Atualiza os valores
  centerReal.x = valor.x;
  centerReal.y = valor.y;
  radius = valor.r * 80;

  // Atualiza os inputs
  centerXInput.value = valor.x.toFixed(2);
  centerYInput.value = valor.y.toFixed(2);
  radiusSlider.value = radius;
  radiusValue.textContent = valor.r.toFixed(2);

  drawCircle();
  drawTransformed();


  // Avança o índice (loop circular)
  indiceAtual = (indiceAtual + 1) % predefinidos.length;
});

canvas2.addEventListener("wheel", function (e) {
  e.preventDefault();
  const zoomIntensity = 0.1;
  const wheel = e.deltaY < 0 ? 1 : -1;
  const zoom = Math.exp(wheel * zoomIntensity);

  const rect = canvas2.getBoundingClientRect();
  const mouseX = e.clientX - rect.left;
  const mouseY = e.clientY - rect.top;

  offsetX2 = mouseX - (mouseX - offsetX2) * zoom;
  offsetY2 = mouseY - (mouseY - offsetY2) * zoom;
  scale2 *= zoom;

  drawTransformed();

}, { passive: false }); // <- ESSENCIAL

canvas1.addEventListener("wheel", function (e) {
  e.preventDefault();
  const zoom = e.deltaY < 0 ? 1.1 : 0.9;
  const mouseX = e.offsetX;
  const mouseY = e.offsetY;

  offsetX1 = mouseX - (mouseX - offsetX1) * zoom;
  offsetY1 = mouseY - (mouseY - offsetY1) * zoom;
  scale1 *= zoom;

  drawCircle();
});

