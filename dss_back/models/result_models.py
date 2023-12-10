from pydantic import BaseModel
from models import TournamentModels, TournamentMechanismResultsModel, KMaxModels, KMaxMechanismResultsModel, \
    LockModels, LockMechanismResultsModel, DominanceModels, DominanceMechanismResultsModel


class TournamentResult(BaseModel):
    type: str = "tournament"
    summary_result: TournamentModels
    result_by_variant: TournamentMechanismResultsModel


class KMaxResult(BaseModel):
    type: str = "K-max"
    summary_result: KMaxModels
    result_by_variant: KMaxMechanismResultsModel
    result_by_variant_optimal: KMaxMechanismResultsModel


class KMaxOptimalResult(BaseModel):
    type: str = "K-max"
    summary_result: KMaxModels
    result_by_variant: KMaxMechanismResultsModel
    result_by_variant_optimal: KMaxMechanismResultsModel


class LockResult(BaseModel):
    type: str = "Lock"
    summary_result: LockModels
    result_by_variant: LockMechanismResultsModel


class DominanceResult(BaseModel):
    type: str = "Dominance"
    summary_result: DominanceModels
    result_by_variant: DominanceMechanismResultsModel