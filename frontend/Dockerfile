# Stage 1: Dependencies
FROM node:22-alpine AS deps
RUN apk add --no-cache libc6-compat
WORKDIR /app

# Set CI to true to avoid TTY issues
ENV CI=true

RUN npm install -g pnpm@10

COPY package.json pnpm-lock.yaml* ./
# Use --frozen-lockfile in CI/Docker to ensure repeatable builds
RUN pnpm install --frozen-lockfile

# Stage 2: Runner
FROM node:22-alpine AS runner
WORKDIR /app

ENV NODE_ENV=production
ENV CI=true

# Create a non-root user for security
RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 starcord

COPY --from=deps /app/node_modules ./node_modules
COPY . .

# Assign ownership to the non-root user
RUN chown -R starcord:nodejs /app
USER starcord

EXPOSE 4200

CMD ["pnpm", "start", "--host", "0.0.0.0", "--disable-host-check"]
